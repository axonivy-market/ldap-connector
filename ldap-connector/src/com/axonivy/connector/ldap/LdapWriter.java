package com.axonivy.connector.ldap;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;

import com.axonivy.connector.ldap.util.JndiConfig;
import com.axonivy.connector.ldap.util.JndiUtil;

public class LdapWriter {

  private JndiConfig jndiConfig;

  public LdapWriter() throws Exception {
    jndiConfig = JndiConfig.create().toJndiConfig();
  }

  public LdapWriter(JndiConfig config) {
    this.jndiConfig = JndiConfig.create(config).toJndiConfig();
  }

  public void createObject(String distinguishedName, Attributes attributes) throws NamingException {
    DirContext dirContext = null;
    try {
      dirContext = JndiUtil.openDirContext(jndiConfig);
      dirContext.createSubcontext(distinguishedName, attributes);
    } finally {
      if (dirContext != null) {
        dirContext.close();
      }

    }

  }

  public void destroyObject(String distinguishedName) throws NamingException {
    DirContext dirContext = null;
    try {
      dirContext = JndiUtil.openDirContext(jndiConfig);
      dirContext.destroySubcontext(distinguishedName);
    } finally {
      if (dirContext != null) {
        dirContext.close();
      }

    }

  }

  public void modifyAttributes(String distinguishedName, int action, Attributes attributes)
          throws NamingException {
    DirContext dirContext = null;
    try {
      dirContext = JndiUtil.openDirContext(jndiConfig);
      dirContext.modifyAttributes(distinguishedName, action, attributes);
    } finally {
      if (dirContext != null) {
        dirContext.close();
      }

    }

  }

}
