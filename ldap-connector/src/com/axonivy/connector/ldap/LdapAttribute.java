package com.axonivy.connector.ldap;

public class LdapAttribute {

  private final String name;
  private final Object value;

  public LdapAttribute(String name, Object value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public Object getValue() {
    return value;
  }

}