package com.axonivy.connector.ldap.util;

import java.util.Hashtable;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class JndiUtil {
  private static DirContext openDirContext(Hashtable<?, ?> environment) throws NamingException {
    return new InitialDirContext(environment);
  }

  public static DirContext openDirContext(JndiConfig jndiConfig) throws NamingException {
    DirContext context = openDirContext(jndiConfig.createEnvironment());
    return context;
  }

}
