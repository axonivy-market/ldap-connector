package com.axonivy.connector.ldap.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Properties;

import javax.naming.directory.SearchControls;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.axonivy.connector.ldap.LdapQuery;
import com.axonivy.connector.ldap.util.JndiConfig;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.environment.IvyTest;
import ch.ivyteam.ivy.scripting.objects.Recordset;
import ch.ivyteam.naming.JndiProvider;

@IvyTest
class TestLdap {

  private static JndiConfig config;
  private static LdapQuery ldapQuery;
  private static String password;
  private static String username;

  private String[] returningAttributes = {"name"};

  @BeforeAll
  static void setup() throws IOException {
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
            .defaultContext("")
            .password(password)
            .provider(JndiProvider.NOVELL_E_DIRECTORY)
            .url(Ivy.var().get("LdapConnector.Url"))
            .useLdapConnectionPool(false)
            .userName(username)
            .useSsl(false).toJndiConfig();
    ldapQuery = new LdapQuery(config);
  }

  @Test
  void person_query() throws Exception {
    Recordset queryResult = ldapQuery.perform("CN=Users,DC=zugtstdomain,DC=wan",
            "(objectClass=person)",
            SearchControls.ONELEVEL_SCOPE,
            returningAttributes);
    assertThat(queryResult.size()).isEqualTo(2);
  }

  @Test
  void group_query() throws Exception {
    Recordset queryResult = ldapQuery.perform("DC=zugtstdomain,DC=wan",
            "(objectClass=group)",
            SearchControls.SUBTREE_SCOPE,
            returningAttributes);
    assertThat(queryResult.size()).isGreaterThan(0);
  }

  @Test
  void empty_result() throws Exception {
    Recordset queryResult = ldapQuery.perform("DC=zugtstdomain,DC=wan",
            "(objectClass=group1xy)",
            SearchControls.SUBTREE_SCOPE,
            returningAttributes);
    assertThat(queryResult.size()).isEqualTo(0);
  }

}
