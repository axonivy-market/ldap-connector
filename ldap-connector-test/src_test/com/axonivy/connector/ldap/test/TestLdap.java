package com.axonivy.connector.ldap.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.utility.DockerImageName;

import com.axonivy.connector.ldap.LdapAttribute;
import com.axonivy.connector.ldap.LdapObject;
import com.axonivy.connector.ldap.LdapQuery;
import com.axonivy.connector.ldap.LdapQueryExecutor;
import com.axonivy.connector.ldap.LdapWriter;
import com.axonivy.connector.ldap.util.JndiConfig;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.environment.IvyTest;

@IvyTest
class TestLdap {
  private static final DockerImageName LDAP_IMAGE = DockerImageName.parse("osixia/openldap:1.5.0");
  private static JndiConfig config;
  private static LdapQueryExecutor queryExecutor;
  private static LdapWriter writer;
  private static String password;
  private static String username;
  private static GenericContainer<?> ldapContainer;
  private SearchControls searchcontrol;
  private LdapQuery query;

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
            .password(password)
            .url(Ivy.var().get("LdapConnector.Url"))
            .userName(username)
            .connectionTimeout(Ivy.var().get("LdapConnector.Connection.Timeout"))
            .provider(Ivy.var().get("LdapConnector.Provider"))
            .referral(Ivy.var().get("LdapConnector.Referral"))
            .toJndiConfig();
    queryExecutor = new LdapQueryExecutor(config);
    writer = new LdapWriter(config);
    
    // Start test LDAP docker container
    ldapContainer = new GenericContainer<>(LDAP_IMAGE)
            .withNetwork(Network.newNetwork())
            .withNetworkAliases("ldap")
            .withExposedPorts(389, 636)
            .withEnv("LDAP_ORGANISATION", "Octopus")
            .withEnv("LDAP_DOMAIN", "com.axonivy")
            .withEnv("LDAP_ADMIN_PASSWORD", config.getPassword())
            .withEnv("LDAP_CONFIG_PASSWORD", config.getPassword())
            .waitingFor(new HttpWaitStrategy()
                    .forPort(389)
                    .forStatusCode(200));

    ldapContainer.start();
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
            .rootObject("CN=Users,DC=zugtstdomain,DC=wan")
            .filter("(objectClass=person)")
            .toLdapQuery();
    List<LdapObject> queryResult = queryExecutor.perform(query);
    assertThat(queryResult).hasSizeGreaterThanOrEqualTo(2);
  }

  @Test
  void group_query() throws Exception {
    query = LdapQuery.create(query)
            .rootObject("DC=zugtstdomain,DC=wan")
            .filter("(objectClass=group)")
            .toLdapQuery();
    List<LdapObject> queryResult = queryExecutor.perform(query);
    assertThat(queryResult).hasSizeGreaterThan(0);
  }

  @Test
  void empty_result() throws Exception {
    query = LdapQuery.create(query)
            .rootObject("DC=zugtstdomain,DC=wan")
            .filter("(objectClass=group1xy)")
            .toLdapQuery();
    List<LdapObject> queryResult = queryExecutor.perform(query);
    assertThat(queryResult).hasSize(0);
  }

  @Test
  void select_single_attribute() throws Exception {
    searchcontrol.setReturningAttributes(new String[] {"name"});
    query = LdapQuery.create(query)
            .rootObject("DC=zugtstdomain,DC=wan")
            .filter("(distinguishedName=CN=Users,CN=Roles,DC=zugtstdomain,DC=wan)")
            .searchControl(searchcontrol)
            .toLdapQuery();
    List<LdapObject> queryResult = queryExecutor.perform(query);
    assertThat(queryResult.get(0).getAttributes()).hasSize(1);
  }

  @Test
  void select_all_attributes() throws Exception {
    query = LdapQuery.create(query)
            .rootObject("DC=zugtstdomain,DC=wan")
            .filter("(distinguishedName=CN=Users,CN=Roles,DC=zugtstdomain,DC=wan)")
            .toLdapQuery();
    List<LdapObject> queryResult = queryExecutor.perform(query);
    assertThat(queryResult.get(0).getAttributes()).hasSizeGreaterThan(1);
  }

  @Test
  void check_attribute_value() throws Exception {
    query = LdapQuery.create(query)
            .rootObject("DC=zugtstdomain,DC=wan")
            .filter("(distinguishedName=CN=Users,CN=Roles,DC=zugtstdomain,DC=wan)")
            .toLdapQuery();
    List<LdapObject> queryResult = queryExecutor.perform(query);
    assertThat(queryResult).hasSize(1);
    assertThat(queryResult.get(0).getAttributes())
            .hasSizeGreaterThan(1)
            .extracting(LdapAttribute::getName, LdapAttribute::getValue)
            .contains(tuple("cn", "Users"));
  }

  @Test
  void create_and_destroy_user() throws NamingException {
    String distinguishedName = "CN=testldap,CN=Users,DC=zugtstdomain,DC=wan";
    query = LdapQuery.create(query)
            .rootObject("DC=zugtstdomain,DC=wan")
            .filter("(distinguishedName=" + distinguishedName + ")")
            .toLdapQuery();
    List<LdapObject> queryResult = queryExecutor.perform(query);
    assertThat(queryResult).isEmpty();

    Attributes newObject = new BasicAttributes("objectClass", "user");
    writer.createObject(distinguishedName, newObject);
    queryResult = queryExecutor.perform(query);
    assertThat(queryResult).hasSize(1);

    writer.destroyObject(distinguishedName);
    queryResult = queryExecutor.perform(query);
    assertThat(queryResult).isEmpty();
  }

  @Test
  void modify_attributes() throws NamingException {
    String distinguishedName = "CN=testldap,CN=Users,DC=zugtstdomain,DC=wan";
    String mail = "ivy@zug.ch";
    query = LdapQuery.create(query)
            .rootObject("DC=zugtstdomain,DC=wan")
            .filter("(distinguishedName=" + distinguishedName + ")")
            .toLdapQuery();

    Attributes newObject = new BasicAttributes("objectClass", "user");
    writer.createObject(distinguishedName, newObject);
    List<LdapObject> queryResult = queryExecutor.perform(query);
    assertThat(queryResult.get(0).getAttributes())
            .hasSizeGreaterThan(1)
            .extracting(LdapAttribute::getName)
            .doesNotContain("mail");

    Attributes newAttribute = new BasicAttributes("mail",mail);
    writer.modifyAttributes(distinguishedName, DirContext.ADD_ATTRIBUTE, newAttribute);
    queryResult = queryExecutor.perform(query);
    assertThat(queryResult.get(0).getAttributes())
            .hasSizeGreaterThan(1)
            .extracting(LdapAttribute::getName, LdapAttribute::getValue)
            .contains(tuple("mail", mail));

    mail = "ivy@luzern.ch";
    newAttribute = new BasicAttributes("mail",mail);
    writer.modifyAttributes(distinguishedName, DirContext.REPLACE_ATTRIBUTE, newAttribute);
    queryResult = queryExecutor.perform(query);
    assertThat(queryResult.get(0).getAttributes())
            .hasSizeGreaterThan(1)
            .extracting(LdapAttribute::getName, LdapAttribute::getValue)
            .contains(tuple("mail", mail));

    newAttribute = new BasicAttributes();
    newAttribute.put(new BasicAttribute("mail"));
    writer.modifyAttributes(distinguishedName, DirContext.REMOVE_ATTRIBUTE, newAttribute);
    queryResult = queryExecutor.perform(query);
    assertThat(queryResult.get(0).getAttributes())
            .hasSizeGreaterThan(1)
            .extracting(LdapAttribute::getName)
            .doesNotContain("mail");

    writer.destroyObject(distinguishedName);
  }

}
