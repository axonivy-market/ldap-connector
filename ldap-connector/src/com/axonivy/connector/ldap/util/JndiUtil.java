package com.axonivy.connector.ldap.util;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class JndiUtil {

  private static final String LDAP_CONNECTION_POOL = "com.sun.jndi.ldap.connect.pool";

  public static DirContext openDirContext(JndiConfig jndiConfig) throws NamingException {
    return new InitialDirContext(createEnvironment(jndiConfig));
  }

  private static Hashtable<?, ?> createEnvironment(JndiConfig jndiConfig) {
    Hashtable<String, Object> env = new Hashtable<String, Object>();
    env.put(Context.INITIAL_CONTEXT_FACTORY,
            jndiConfig.getProvider().getProviderClass());
    env.put(Context.PROVIDER_URL, jndiConfig.getUrl());
    if (jndiConfig.getAuthenticationKind().equals(JndiConfig.AUTH_KIND_SIMPLE)) {
      env.put(Context.SECURITY_AUTHENTICATION, JndiConfig.AUTH_KIND_SIMPLE);
      env.put(Context.SECURITY_PRINCIPAL, jndiConfig.getUserName());
      env.put(Context.SECURITY_CREDENTIALS, jndiConfig.getPassword());
    } else {
      env.put(Context.SECURITY_AUTHENTICATION, JndiConfig.AUTH_KIND_NONE);
    }
    if (jndiConfig.isUseSsl()) {
      env.put(Context.SECURITY_PROTOCOL, "ssl");
    }
    if (jndiConfig.isUseLdapConnectionPool()) {
      env.put(LDAP_CONNECTION_POOL, "true");
    }
    env.put(Context.REFERRAL, "follow");
    env.put("com.sun.jndi.ldap.connect.timeout", "1000");
    return env;
  }
}
