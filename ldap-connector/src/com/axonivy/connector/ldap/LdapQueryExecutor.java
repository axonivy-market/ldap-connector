package com.axonivy.connector.ldap;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchResult;

import com.axonivy.connector.ldap.util.JndiConfig;
import com.axonivy.connector.ldap.util.JndiUtil;

public class LdapQueryExecutor {

  private JndiConfig jndiConfig;

  public LdapQueryExecutor() throws Exception {
    jndiConfig = JndiConfig.create().toJndiConfig();
  }

  public LdapQueryExecutor(JndiConfig config) {
    this.jndiConfig = JndiConfig.create(config).toJndiConfig();
  }

  public List<LdapObject> perform(LdapQuery ldapQuery)
          throws NamingException {
    DirContext dirContext = null;
    NamingEnumeration<SearchResult> searchResults = null;
    List<LdapObject> ldapObjectList;
    try {
      dirContext = JndiUtil.openDirContext(jndiConfig);
      searchResults = dirContext.search(ldapQuery.getRootObject(), ldapQuery.getFilter(),
              ldapQuery.getSearchControl());
      ldapObjectList = searchResultsToLdapObjectList(searchResults);
    } finally {
      if (dirContext != null) {
        dirContext.close();
      }

      if (searchResults != null) {
        searchResults.close();
      }
    }
    return ldapObjectList;
  }

  private static List<LdapObject> searchResultsToLdapObjectList(
          NamingEnumeration<SearchResult> searchResults) throws NamingException {
    ArrayList<LdapObject> ldapObjectsList = new ArrayList<LdapObject>();

    while (searchResults.hasMore()) {
      SearchResult searchResult = searchResults.nextElement();
      LdapObject ldapObject = searchResultToLadpObject(searchResult);
      ldapObjectsList.add(ldapObject);
    }
    return ldapObjectsList;
  }

  private static LdapObject searchResultToLadpObject(SearchResult searchResult) throws NamingException {
    LdapObject ldapObject = new LdapObject();
    NamingEnumeration<?> attributes = searchResult.getAttributes().getAll();

    while (attributes.hasMore()) {
      Attribute attribute = (Attribute) attributes.next();

      NamingEnumeration<?> attributeValues = attribute.getAll();
      while (attributeValues.hasMore()) {
        ldapObject.getAttributes().add(new LdapAttribute(attribute.getID(), attributeValues.next()));
      }
    }
    return ldapObject;
  }
}