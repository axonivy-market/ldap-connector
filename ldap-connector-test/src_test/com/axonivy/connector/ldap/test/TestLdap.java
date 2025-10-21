package com.axonivy.connector.ldap.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import com.axonivy.connector.ldap.LdapAttribute;
import com.axonivy.connector.ldap.LdapObject;
import com.axonivy.connector.ldap.LdapQuery;
import com.axonivy.connector.ldap.LdapQueryExecutor;
import com.axonivy.connector.ldap.LdapWriter;
import com.axonivy.connector.ldap.util.JndiConfig;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.environment.IvyTest;

@IvyTest
class TestLdap {
  private static final DockerImageName LDAP_IMAGE = DockerImageName.parse("osixia/openldap:latest");
  private static final String DOMAIN_COMPONENT = "dc=zugtstdomain,dc=wan";
  private static final String FALSE_VALUE = "false";
  private static final String TRUE_VALUE = "true";
  private static final String USERS_FILTER = "(ou=Users)";
  private static final String OBJECT_CLASS = "objectClass";
  private static final String INET_ORG_PERSON = "inetOrgPerson";

  private static JndiConfig config;
  private static LdapQueryExecutor queryExecutor;
  private static LdapWriter writer;
  private static String password;
  private static String username;
  private static GenericContainer<?> ldapContainer;
  private SearchControls searchcontrol;
  private LdapQuery query;

  @SuppressWarnings("resource")
  @BeforeAll
  static void setupConfig() throws IOException {
    username = System.getProperty("adUsername");
    password = System.getProperty("adPassword");

    if (StringUtils.isEmpty(username)) {
      try (var in = TestLdap.class.getResourceAsStream("credentials.properties")) {
        if (in != null) {
          Properties props = new Properties();
          props.load(in);
          username = (String) props.get("username");
          password = (String) props.get("password");
        }
      }
    }
    config = JndiConfig.create()
            .password(password).url(Ivy.var().get("LdapConnector.Url"))
            .userName("cn=admin," + DOMAIN_COMPONENT)
            .connectionTimeout(Ivy.var().get("LdapConnector.Connection.Timeout"))
            .provider(Ivy.var().get("LdapConnector.Provider"))
            .referral(Ivy.var().get("LdapConnector.Referral"))
            .toJndiConfig();
    queryExecutor = new LdapQueryExecutor(config);
    writer = new LdapWriter(config);
    
    Network network = Network.newNetwork();
    ldapContainer = new GenericContainer<>(LDAP_IMAGE).withNetwork(network).withNetworkAliases("octopus_ldap")
        .withExposedPorts(389)
        .withCreateContainerCmdModifier(
            cmd -> cmd.withHostConfig(HostConfig.newHostConfig().withNetworkMode(network.getId())
                .withPortBindings(
                    new PortBinding(Ports.Binding.bindPort(389), new ExposedPort(389)))))
        .withEnv("LDAP_ORGANISATION", "example")
        .withEnv("LDAP_DOMAIN", "zugtstdomain.wan")
        .withEnv("LDAP_ADMIN_PASSWORD", password)
        .withEnv("LDAP_CONFIG_PASSWORD", "config")
        .withEnv("LDAP_READONLY_USER", FALSE_VALUE)
        .withEnv("LDAP_RFC2307BIS_SCHEMA", FALSE_VALUE)
        .withEnv("LDAP_BACKEND", "mdb")
        .withEnv("LDAP_TLS", FALSE_VALUE)
        .withEnv("LDAP_REPLICATION", FALSE_VALUE)
        .withEnv("KEEP_EXISTING_CONFIG", FALSE_VALUE)
        .withEnv("LDAP_REMOVE_CONFIG_AFTER_SETUP", TRUE_VALUE)
        .withCopyFileToContainer(
            MountableFile.forHostPath(java.nio.file.Paths.get("").toAbsolutePath().resolve("../ldap-connector-demo/docker/octopus.ldif")),
            "/container/service/slapd/assets/config/bootstrap/schema/custom/octopus.ldif")
        .withCopyFileToContainer(
            MountableFile.forHostPath(java.nio.file.Paths.get("").toAbsolutePath().resolve("../ldap-connector-demo/docker/ldifs/data.ldif")),
            "/container/service/slapd/assets/config/bootstrap/ldif/custom/data.ldif")
        .withCommand("--copy-service")
        .waitingFor(Wait.forLogMessage(".*slapd starting.*", 1))
        .withStartupTimeout(Duration.ofMinutes(2));
    ldapContainer.start();
  }

  @AfterAll
  static void clearTestContainer() {
    ldapContainer.close();
  }

  @BeforeEach
  void setup() {
    searchcontrol = new SearchControls();
    searchcontrol.setSearchScope(SearchControls.SUBTREE_SCOPE);

    query = LdapQuery.create()
            .searchControl(searchcontrol)
            .toLdapQuery();
  }

  @Test
  void person_query() throws Exception {
    query = LdapQuery.create(query)
            .rootObject("ou=Users," + DOMAIN_COMPONENT)
            .filter("(" + OBJECT_CLASS + "=" + INET_ORG_PERSON + ")")
            .toLdapQuery();
    List<LdapObject> queryResult = queryExecutor.perform(query);
    assertThat(queryResult).hasSizeGreaterThanOrEqualTo(4);
    
    assertThat(queryResult)
            .extracting(obj -> obj.getAttributes().stream()
                    .filter(attr -> "cn".equals(attr.getName()))
                    .map(LdapAttribute::getValue)
                    .findFirst().orElse(""))
            .contains("Dino", "Octopus", "TestUser1", "TestUser2");
  }

  @Test
  void group_query() throws Exception {
    query = LdapQuery.create(query)
            .rootObject(DOMAIN_COMPONENT)
            .filter("(" + OBJECT_CLASS + "=groupOfNames)")
            .toLdapQuery();
    List<LdapObject> queryResult = queryExecutor.perform(query);
    assertThat(queryResult).hasSizeGreaterThanOrEqualTo(2);
    
    assertThat(queryResult)
            .extracting(obj -> obj.getAttributes().stream()
                    .filter(attr -> "cn".equals(attr.getName()))
                    .map(LdapAttribute::getValue)
                    .findFirst().orElse(""))
            .contains("Users", "TestGroup");
  }

  @Test
  void empty_result() throws Exception {
    query = LdapQuery.create(query)
            .rootObject(DOMAIN_COMPONENT)
            .filter("(" + OBJECT_CLASS + "=nonexistent)")
            .toLdapQuery();
    List<LdapObject> queryResult = queryExecutor.perform(query);
    assertThat(queryResult).hasSize(0);
  }

  @Test
  void select_single_attribute() throws Exception {
    searchcontrol.setReturningAttributes(new String[] {"ou"});
    query = LdapQuery.create(query)
            .rootObject(DOMAIN_COMPONENT)
            .filter(USERS_FILTER)
            .searchControl(searchcontrol)
            .toLdapQuery();
    List<LdapObject> queryResult = queryExecutor.perform(query);
    assertThat(queryResult.get(0).getAttributes()).hasSize(1);
  }

  @Test
  void select_all_attributes() throws Exception {
    query = LdapQuery.create(query)
            .rootObject(DOMAIN_COMPONENT)
            .filter(USERS_FILTER)
            .toLdapQuery();
    List<LdapObject> queryResult = queryExecutor.perform(query);
    assertThat(queryResult.get(0).getAttributes()).hasSizeGreaterThan(1);
  }

  @Test
  void check_attribute_value() throws Exception {
    query = LdapQuery.create(query)
            .rootObject(DOMAIN_COMPONENT)
            .filter("(" + OBJECT_CLASS + "=*)")
            .toLdapQuery();
    List<LdapObject> queryResult = queryExecutor.perform(query);
    
    assertThat(queryResult).hasSizeGreaterThanOrEqualTo(10);
    
    query = LdapQuery.create(query)
            .rootObject(DOMAIN_COMPONENT)
            .filter(USERS_FILTER)
            .toLdapQuery();
    List<LdapObject> usersResult = queryExecutor.perform(query);
    assertThat(usersResult).hasSize(1);
  }

  @Test
  void create_and_destroy_user() throws NamingException {
    String distinguishedName = "cn=tempUser,ou=Users," + DOMAIN_COMPONENT;
    query = LdapQuery.create(query)
            .rootObject(DOMAIN_COMPONENT)
            .filter("(cn=tempUser)").toLdapQuery();
    List<LdapObject> queryResult = queryExecutor.perform(query);
    assertThat(queryResult).isEmpty();

    Attributes newObject = new BasicAttributes();
    newObject.put(OBJECT_CLASS, INET_ORG_PERSON);
    newObject.put("cn", "tempUser");
    newObject.put("sn", "tempUser");
    writer.createObject(distinguishedName, newObject);
    queryResult = queryExecutor.perform(query);
    assertThat(queryResult).hasSize(1);

    writer.destroyObject(distinguishedName);
    queryResult = queryExecutor.perform(query);
    assertThat(queryResult).isEmpty();
  }

  @Test
  void modify_attributes() throws NamingException {
    String distinguishedName = "cn=Dino,ou=Users," + DOMAIN_COMPONENT;
    String newMail = "dino.modified@zug.ch";
    query = LdapQuery.create(query)
            .rootObject(DOMAIN_COMPONENT)
            .filter("(cn=Dino)")
            .toLdapQuery();

    List<LdapObject> queryResult = queryExecutor.perform(query);
    assertThat(queryResult).hasSize(1);
    assertThat(queryResult.get(0).getAttributes())
            .extracting(LdapAttribute::getName, LdapAttribute::getValue)
            .contains(tuple("mail", "dino@zugtstdomain.wan"));

    Attributes newAttribute = new BasicAttributes("mail", newMail);
    writer.modifyAttributes(distinguishedName, DirContext.REPLACE_ATTRIBUTE, newAttribute);
    queryResult = queryExecutor.perform(query);
    assertThat(queryResult.get(0).getAttributes())
            .extracting(LdapAttribute::getName, LdapAttribute::getValue)
            .contains(tuple("mail", newMail));

    newAttribute = new BasicAttributes();
    newAttribute.put(new BasicAttribute("mail"));
    writer.modifyAttributes(distinguishedName, DirContext.REMOVE_ATTRIBUTE, newAttribute);
    queryResult = queryExecutor.perform(query);
    assertThat(queryResult.get(0).getAttributes())
            .extracting(LdapAttribute::getName)
            .doesNotContain("mail");

    newAttribute = new BasicAttributes("mail", "dino@zugtstdomain.wan");
    writer.modifyAttributes(distinguishedName, DirContext.ADD_ATTRIBUTE, newAttribute);
  }

}
