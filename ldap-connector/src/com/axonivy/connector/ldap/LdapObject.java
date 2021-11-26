package com.axonivy.connector.ldap;

import java.util.ArrayList;
import java.util.List;

public class LdapObject {

  private final List<LdapAttribute> attributes = new ArrayList<LdapAttribute>();

  public List<LdapAttribute> getAttributes() {
    return attributes;
  }
}