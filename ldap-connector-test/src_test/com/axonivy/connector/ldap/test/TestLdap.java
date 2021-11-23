package com.axonivy.connector.ldap.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Properties;

import javax.naming.NamingEnumeration;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.axonivy.connector.ldap.LdapQueryBeanRS;
import com.axonivy.connector.ldap.util.JndiConfig;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.environment.IvyTest;
import ch.ivyteam.naming.JndiProvider;

@IvyTest
class TestLdap {

  private JndiConfig config;
  private LdapQueryBeanRS ldapQuery;
  private String[] returningAttributes;
  private String password;
  private String username;

  @BeforeAll
  void getCredentials() throws IOException {
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
  }

  @BeforeEach
  void setup() {
    config = JndiConfig.create()
            .authenticationKind(JndiConfig.AUTH_KIND_SIMPLE)
            .defaultContext("")
            .password(password)
            .provider(JndiProvider.NOVELL_E_DIRECTORY)
            .url(Ivy.var().get("LdapConnector.Url"))
            .useLdapConnectionPool(false)
            .userName(username)
            .useSsl(false).toJndiConfig();
    ldapQuery = new LdapQueryBeanRS(config);
  }

  @Test
  void admin_query() throws Exception {
    NamingEnumeration<SearchResult> resultEnum = ldapQuery.perform("DC=zugtstdomain,DC=wan", "(name=admin)",
            SearchControls.ONELEVEL_SCOPE,
            returningAttributes);
    assertThat(resultEnum.hasMoreElements()).isTrue();
  }

  @Test
  void query2() throws Exception {
    NamingEnumeration<SearchResult> resultEnum = ldapQuery.perform("DC=zugtstdomain,DC=wan",
            "(objectClass=*)",
            SearchControls.SUBTREE_SCOPE,
            returningAttributes);

    while (resultEnum.hasMoreElements()) {
      SearchResult r = resultEnum.next();
      System.out.println(r);
      System.out.println(r.getAttributes().get("name"));
    }
  }

}
