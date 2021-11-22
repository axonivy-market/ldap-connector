package com.axonivy.connector.ldap.test;

import org.junit.jupiter.api.Test;

import com.axonivy.connector.ldap.LdapQueryBeanRS;
import com.axonivy.connector.ldap.util.JndiConfig;


class TestLdap {


  // before

  // var config = new JndiConfig(null, null, null, null, null, false, false, null);
  // var ldap = new LdapQueryBeanRS(config);


  @Test
  void ldap() throws Exception {
    var config = new JndiConfig(null, null, null, null, null, false, false, null);
    var ldap = new LdapQueryBeanRS(config);
    ldap.setConfi();

  }

}
