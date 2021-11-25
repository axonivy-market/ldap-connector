package com.axonivy.connector.ldap.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.naming.directory.SearchControls;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.axonivy.connector.ldap.LdapAttribute;
import com.axonivy.connector.ldap.LdapObject;
import com.axonivy.connector.ldap.LdapQuery;
import com.axonivy.connector.ldap.LdapQueryExecuter;
import com.axonivy.connector.ldap.util.JndiConfig;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.environment.IvyTest;
import ch.ivyteam.naming.JndiProvider;

@IvyTest
class TestLdap {
  private static JndiConfig config;
  private static LdapQueryExecuter queryExecuter;
  private static String password;
  private static String username;

  private SearchControls searchcontrol;

  @BeforeAll
  static void setupConfig() throws IOException {
    username = Ivy.var().get("LdapConnector.Username");
    password = Ivy.var().get("LdapConnector.Password");

    if (StringUtils.isEmpty(username)) {
      try (var in = TestLdap.class.getResourceAsStream("credentials.properties")) {
        Properties props = new Properties();
        props.load(in);
        username = (String) props.get("username");
        password = (String) props.get("password");
      }
    }
    config = JndiConfig.create()
            .authenticationKind(JndiConfig.AUTH_KIND_SIMPLE)
            .password(password)
            .provider(JndiProvider.NOVELL_E_DIRECTORY)
            .url(Ivy.var().get("LdapConnector.Url"))
            .useLdapConnectionPool(false)
            .userName(username)
            .useSsl(false).toJndiConfig();
    queryExecuter = new LdapQueryExecuter(config);
  }

  @BeforeEach
  void setup() {
    searchcontrol = new SearchControls();
    searchcontrol.setSearchScope(SearchControls.SUBTREE_SCOPE);
  }

  @Test
  void person_query() throws Exception {
    LdapQuery query = LdapQuery.create()
            .rootObject("CN=Users,DC=zugtstdomain,DC=wan")
            .filter("(objectClass=person)")
            .searchControl(searchcontrol)
            .toLdapQuery();
    List<LdapObject> queryResult = queryExecuter.perform(query);
    assertThat(queryResult.size()).isEqualTo(2);

  }

  @Test
  void group_query() throws Exception {
    LdapQuery query = LdapQuery.create()
            .rootObject("DC=zugtstdomain,DC=wan")
            .filter("(objectClass=group)")
            .searchControl(searchcontrol)
            .toLdapQuery();
    List<LdapObject> queryResult = queryExecuter.perform(query);
    assertThat(queryResult.size()).isGreaterThan(0);
  }

  @Test
  void empty_result() throws Exception {
    LdapQuery query = LdapQuery.create()
            .rootObject("DC=zugtstdomain,DC=wan")
            .filter("(objectClass=group1xy)")
            .searchControl(searchcontrol)
            .toLdapQuery();
    List<LdapObject> queryResult = queryExecuter.perform(query);
    assertThat(queryResult.size()).isEqualTo(0);
  }

  @Test
  void select_single_attribute() throws Exception {
    searchcontrol.setReturningAttributes(new String[] {"name"});
    LdapQuery query = LdapQuery.create()
            .rootObject("DC=zugtstdomain,DC=wan")
            .filter("(distinguishedName=CN=Users,CN=Roles,DC=zugtstdomain,DC=wan)")
            .searchControl(searchcontrol)
            .toLdapQuery();
    List<LdapObject> queryResult = queryExecuter.perform(query);
    assertThat(queryResult.get(0).getAttributes().size()).isEqualTo(1);
  }

  @Test
  void select_all_attributes() throws Exception {
    LdapQuery query = LdapQuery.create()
            .rootObject("DC=zugtstdomain,DC=wan")
            .filter("(distinguishedName=CN=Users,CN=Roles,DC=zugtstdomain,DC=wan)")
            .searchControl(searchcontrol)
            .toLdapQuery();
    List<LdapObject> queryResult = queryExecuter.perform(query);
    assertThat(queryResult.get(0).getAttributes().size()).isGreaterThan(1);
  }

  @Test
  void check_attribute_value() throws Exception {
    LdapQuery query = LdapQuery.create()
            .rootObject("DC=zugtstdomain,DC=wan")
            .filter("(distinguishedName=CN=Users,CN=Roles,DC=zugtstdomain,DC=wan)")
            .searchControl(searchcontrol)
            .toLdapQuery();
    List<LdapObject> queryResult = queryExecuter.perform(query);
    assertThat(queryResult.size()).isEqualTo(1);
    List<LdapAttribute> filteredAttributes = queryResult.get(0).getAttributes().stream()
            .filter(a -> a.getName().equals("cn") && a.getValue().equals("Users"))
            .collect(Collectors.toList());
    assertThat(filteredAttributes.size()).isEqualTo(1);
  }

}
