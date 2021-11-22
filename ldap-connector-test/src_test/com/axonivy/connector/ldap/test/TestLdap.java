package com.axonivy.connector.ldap.test;

import static org.assertj.core.api.Assertions.assertThat;

import javax.naming.NamingEnumeration;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.axonivy.connector.ldap.LdapQueryBeanRS;
import com.axonivy.connector.ldap.util.JndiConfig;

import ch.ivyteam.naming.JndiProvider;

class TestLdap {

  private JndiConfig config;
  private LdapQueryBeanRS ldapQuery;
  private String[] returningAttributes;

  @BeforeEach
  void setup() {
    config = JndiConfig.create()
            .authenticationKind(JndiConfig.AUTH_KIND_SIMPLE)
            .defaultContext("")
            .password("nimda")
            .provider(JndiProvider.ACTIVE_DIRECTORY)
            .url("ldap://test-ad.ivyteam.io:389")
            .useLdapConnectionPool(false)
            .userName("cn=admin,cn=Users,dc=zugtstdomain,dc=wan")
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

}
