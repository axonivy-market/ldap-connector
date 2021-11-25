package com.axonivy.connector.ldap;

import javax.naming.directory.SearchControls;

import org.apache.commons.lang3.StringUtils;

public class LdapQuery {

  private String rootObject;
  private String filter;
  private SearchControls searchControl;

  public LdapQuery(String rootObject, String filter, SearchControls searchcontrol) {
    this.rootObject = rootObject;
    this.filter = filter;
    this.searchControl = searchcontrol;
  }

  public String getRootObject() {
    return rootObject;
  }

  public SearchControls getSearchControl() {
    return searchControl;
  }

  public String getFilter() {
    return filter;
  }

  public static Builder create() {
    return new Builder();
  }

  public static Builder create(LdapQuery ldapQuery) {
    return new Builder()
            .rootObject(ldapQuery.rootObject)
            .filter(ldapQuery.filter)
            .searchControl(ldapQuery.searchControl);
  }

  @SuppressWarnings("hiding")
  public static final class Builder {
    private String rootObject = "";
    private String filter = "";
    private SearchControls searchControl = new SearchControls();

    public Builder rootObject(String rootObject) {
      this.rootObject = StringUtils.defaultString(rootObject);
      return this;
    }

    public Builder searchControl(SearchControls searchControl) {
      if (searchControl != null) {
        this.searchControl = searchControl;
      }
      return this;
    }

    public Builder filter(String filter) {
      this.filter = StringUtils.defaultString(filter);
      return this;
    }

    public LdapQuery toLdapQuery() {
      return new LdapQuery(rootObject, filter, searchControl);
    }

  }

}
