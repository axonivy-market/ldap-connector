package com.axonivy.connector.ldap.util;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.commons.lang3.StringUtils;

public class JndiUtil {

  private static final String LDAP_CONNECTION_TIMEOUT = "com.sun.jndi.ldap.connect.timeout";

  public static DirContext openDirContext(JndiConfig jndiConfig) throws NamingException {
    return new InitialDirContext(createEnvironment(jndiConfig));
  }

  private static Hashtable<?, ?> createEnvironment(JndiConfig jndiConfig) {
    Hashtable<String, Object> env = new Hashtable<String, Object>();
    env.put(Context.INITIAL_CONTEXT_FACTORY,
            jndiConfig.getProvider());
    env.put(Context.PROVIDER_URL, jndiConfig.getUrl());
    if (StringUtils.isBlank(jndiConfig.getUserName())) {
      env.put(Context.SECURITY_AUTHENTICATION, "none");
    } else {
      env.put(Context.SECURITY_AUTHENTICATION, "simple");
      env.put(Context.SECURITY_PRINCIPAL, jndiConfig.getUserName());
      env.put(Context.SECURITY_CREDENTIALS, jndiConfig.getPassword());
    }
    if (StringUtils.startsWithIgnoreCase(jndiConfig.getUrl(), "ldaps")) {
      env.put(Context.SECURITY_PROTOCOL, "ssl");
    }
    env.put(Context.REFERRAL, jndiConfig.getReferral());
    env.put(LDAP_CONNECTION_TIMEOUT, jndiConfig.getConnectionTimeout());
    return env;
  }
}
