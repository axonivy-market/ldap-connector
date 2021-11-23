package com.axonivy.connector.ldap;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import com.axonivy.connector.ldap.util.JndiConfig;
import com.axonivy.connector.ldap.util.JndiUtil;

import ch.ivyteam.ivy.persistence.PersistencyException;
import ch.ivyteam.ivy.process.engine.IRequestId;
import ch.ivyteam.ivy.scripting.exceptions.IvyScriptException;
import ch.ivyteam.ivy.scripting.language.IIvyScriptContext;
import ch.ivyteam.ivy.scripting.objects.CompositeObject;
import ch.ivyteam.ivy.scripting.objects.List;
import ch.ivyteam.ivy.scripting.objects.Recordset;

public class LdapQuery {

  private JndiConfig jndiConfig;
  private Hashtable<String, String> resultAttributesHashtable = new Hashtable<>();
  private Vector<String> resultAttributesKeys = new Vector<>();
  private String ivyGridAttribute = null;
  private boolean includeName;
  private String ivyGridNameAttribute;
  private String sortByAttribute;
  private boolean descendingSort;

  public LdapQuery() throws Exception {
    jndiConfig = JndiConfig.create().toJndiConfig();
  }

  public LdapQuery(JndiConfig config) {
    this.jndiConfig = JndiConfig.create(config).toJndiConfig();
  }

  public NamingEnumeration<SearchResult> perform(String rootObject, String filter, int searchScope,
          String[] returningAttributes)
          throws NamingException {
    DirContext dirContext = JndiUtil.openDirContext(jndiConfig);
    SearchControls searchControls = defineSearchControl(searchScope, returningAttributes);
    NamingEnumeration<SearchResult> resultEnum = dirContext.search(rootObject, filter, searchControls);
    dirContext.close();
    return resultEnum;
  }

  private static SearchControls defineSearchControl(int searchScope, String[] returningAttributes) {
    SearchControls searchControl = new SearchControls();
    searchControl.setSearchScope(searchScope);
    searchControl.setReturningAttributes(returningAttributes);
    // TODO check effect?
    searchControl.setReturningObjFlag(false);

    return searchControl;
  }

  public CompositeObject perform(IRequestId reqID, CompositeObject argument,
          IIvyScriptContext cont) throws Exception {
    final String filter = "";//buildSearchFilter(cont);
    final String objectName = "";//getRootObjectName(cont);
    final JndiConfig expandedJndiConfig = createJndiConfig(cont);

    DirContext dirContext = null;
    NamingEnumeration<SearchResult> resultEnum = null;
    try {
      // query the naming and directory service
      // dirContext = new
      // InitialDirContext(expandedJndiConfig.getEnvironement()); // this only
      // works in Xivy version < 4.3.15
      dirContext = JndiUtil.openDirContext(expandedJndiConfig);
      resultEnum = dirContext.search(objectName, filter, null);

      Vector<Vector<Object>> result = null;
      if (ivyGridAttribute != null) {
        result = new Vector<>();
      }

      // read the result and assign them to the ivyGrid arguments
      if (resultEnum == null || !resultEnum.hasMoreElements()) {
        setNoResult(argument);
      } else {
        Vector<Object> row = null;
        while (resultEnum.hasMoreElements()) {
          SearchResult searchResult = resultEnum.nextElement();
          appendSearchResultToRow(argument, objectName, searchResult, row);
          if (ivyGridAttribute == null) {
            return argument;
          }
          if (result != null) {
            result.add(row);
          }
        }
      }

      if (ivyGridAttribute != null) {
        if (Recordset.class.equals(getVariable(ivyGridAttribute, cont).getClass())) {
          Recordset recordset = mapToRecordsetAttribute(cont, result);
          setVariable(ivyGridAttribute, recordset, argument);
        } else { // return as list[list]
          setVariable(ivyGridAttribute, result, argument);
        }
      }
    } finally {
      closeHandlesSilently(dirContext, resultEnum);
    }

    return argument;
  }

  private JndiConfig createJndiConfig(IIvyScriptContext cont) {
    /*
     * JndiConfig expandedJndiConfig = (JndiConfig) jndiConfig.clone(); // try
     * to expand url, name and password fields String propStr =
     * expandedJndiConfig.getUrl(); if (propStr != null) { if
     * (getVariable(propStr, cont) != null) { propStr = (String)
     * getVariable(propStr, cont); expandedJndiConfig.setUrl(propStr); } }
     * propStr = expandedJndiConfig.getUserName(); if (propStr != null) { if
     * (getVariable(propStr, cont) != null) { propStr = (String)
     * getVariable(propStr, cont); expandedJndiConfig.setUserName(propStr); } }
     * propStr = expandedJndiConfig.getPassword(); if (propStr != null) { if
     * (getVariable(propStr, cont) != null) { propStr = (String)
     * getVariable(propStr, cont); expandedJndiConfig.setPassword(propStr); } }
     */
    return jndiConfig;
    // return expandedJndiConfig;
  }

  private void setNoResult(CompositeObject argument) throws NoSuchFieldException {
    // no result found! --> set output to null
    if (ivyGridAttribute == null) {
      Enumeration<String> attrEnum = resultAttributesKeys.elements();
      while (attrEnum.hasMoreElements()) {
        String attribute = attrEnum.nextElement();
        setVariable(resultAttributesHashtable.get(attribute), null, argument);
      }
      if (includeName) {
        setVariable(ivyGridNameAttribute, null, argument);
      }
    }
  }

  private Vector<String> readColumnNames() {
    Vector<String> colNames = new Vector<>();
    if (includeName) {
      colNames.add("JNDIName");
    }
    Enumeration<String> attrEnum = resultAttributesKeys.elements();
    while (attrEnum.hasMoreElements()) {
      String attribute = attrEnum.nextElement();
      colNames.add(attribute);
    }
    return colNames;
  }

  private Recordset mapToRecordsetAttribute(IIvyScriptContext cont, Vector<Vector<Object>> result) {
    if (result == null || result.size() == 0) {
      return null;
    }

    Vector<String> colNames = readColumnNames();
    int sortCol = 0;
    if (getVariable(sortByAttribute, cont) != null) {
      sortByAttribute = getVariable(sortByAttribute, cont).toString();
    }
    if (sortByAttribute != null) {
      for (int c = 0; c < colNames.size(); c++) {
        if (sortByAttribute.equals(colNames.elementAt(c))) {
          sortCol = c;
          break;
        }
      }
    }
    Object[][] data = new Object[result.size()][colNames.size()];
    for (int r = 0; r < result.size(); r++) {
      Vector<Object> aRow = result.elementAt(r);
      int insertAt = 0;
      if (sortByAttribute == null) {
        insertAt = r;
      } else if (descendingSort) {
        while (insertAt < r
                && aRow.elementAt(sortCol) != null
                && data[insertAt][sortCol].toString()
                        .compareToIgnoreCase(
                                aRow.elementAt(sortCol)
                                        .toString()) > 0) {
          insertAt++;
        }
      } else { // ascending
        while (insertAt < r
                && aRow.elementAt(sortCol) != null
                && data[insertAt][sortCol].toString()
                        .compareToIgnoreCase(
                                aRow.elementAt(sortCol)
                                        .toString()) < 0) {
          insertAt++;
        }
      }
      for (int c = 0; c < colNames.size(); c++) {
        for (int shift = 0; insertAt < (r - shift); shift++) {
          data[r - shift][c] = data[r - shift - 1][c];
        }
        data[insertAt][c] = aRow.elementAt(c);
      }
    }

    List<String> keyList = List.create(String.class);
    keyList.addAll(colNames);
    Recordset returnRS = new Recordset(keyList);
    for (int j = 0; j < data.length; j++) {
      List<Object> valueList = List.create();
      for (Object val : data[j]) {
        if (val != null)
          valueList.add(val);
      }
      returnRS.add(valueList);
    }

    return returnRS;
  }

  private void appendSearchResultToRow(CompositeObject argument, String objectName,
          SearchResult searchResult,
          Vector<Object> row) throws NoSuchFieldException, NamingException {
    if (searchResult == null) {
      return;
    }

    if (ivyGridAttribute != null) {
      row = new Vector<>();
    }

    // include jndi name in result if it is selected
    if (includeName) {
      addJndiName(argument, objectName, searchResult, row);
    }

    Attributes jndiAttributes = searchResult.getAttributes();
    Enumeration<String> attrEnum = resultAttributesKeys.elements();
    while (attrEnum.hasMoreElements()) {
      String resultAttrName = attrEnum.nextElement();
      Attribute jndiAttribute = getJndiAttribute(jndiAttributes, resultAttrName);
      appendAttributeEntry(argument, row, resultAttrName, jndiAttribute);
    }
  }

  private void addJndiName(CompositeObject argument, String objectName, SearchResult searchResult,
          Vector<Object> row) throws NoSuchFieldException {
    String resultObjectName = searchResult.getName();
    if (resultObjectName != null) {
      resultObjectName = resultObjectName.replaceAll(
              "/", "\\\\/");
      if (resultObjectName.startsWith("\"")
              && resultObjectName.endsWith("\"")) {
        resultObjectName = resultObjectName
                .substring(1, resultObjectName
                        .length() - 1);
      }
    }

    if (ivyGridAttribute != null) {
      if (objectName.trim().equals("")) {
        row.add(resultObjectName);
      } else {
        row.add(resultObjectName + ","
                + objectName);
      }
    } else {
      if (objectName.trim().equals("")) {
        setVariable(ivyGridNameAttribute,
                resultObjectName, argument);
      } else {
        setVariable(ivyGridNameAttribute,
                resultObjectName + ","
                        + objectName,
                argument);
      }
    }
  }

  private static Attribute getJndiAttribute(Attributes jndiAttributes, String resultAttrName) {
    if (jndiAttributes == null) {
      return null;
    }
    return jndiAttributes.get(resultAttrName);
  }

  private void appendAttributeEntry(CompositeObject argument, Vector<Object> row, String resultAttrName,
          Attribute jndiAttribute) throws NamingException, NoSuchFieldException {
    if (jndiAttribute == null) {
      // none
      if (ivyGridAttribute != null) {
        row.add("");
      }
      return;
    }

    if (jndiAttribute.size() == 1) {
      // single
      if (ivyGridAttribute != null) {
        row.add(jndiAttribute.get());
      } else {
        setVariable(
                resultAttributesHashtable.get(resultAttrName),
                jndiAttribute.get(), argument);
      }
    } else if (jndiAttribute.size() > 1) {
      // multiple
      if (ivyGridAttribute != null) {
        NamingEnumeration<?> tmpEnum = jndiAttribute.getAll();
        List<String> l = List.create(String.class);
        while (tmpEnum.hasMoreElements()) {
          String tmpStr = (String) tmpEnum.nextElement();
          l.add(tmpStr);
        }
        row.add(l);
      } else {
        setVariable(
                resultAttributesHashtable.get(resultAttrName),
                jndiAttribute.getAll(), argument);
      }
    }
  }

  private static void closeHandlesSilently(DirContext dirContext,
          NamingEnumeration<SearchResult> resultEnum) {
    if (resultEnum != null) {
      try {
        resultEnum.close();
      } catch (NamingException ex) {
      }
    }
    if (dirContext != null) {
      try {
        dirContext.close();
      } catch (NamingException ex) {
      }
    }
  }

  public Object getVariable(String name, IIvyScriptContext cont) {
    try {
      if (name.contains("in."))
        return null; // this.executeIvyScript(cont, name);
      else
        return null; // this.executeIvyScript(cont, "in." + name);
    } catch (PersistencyException e) {
      return name;
    }
  }

  public void setVariable(String name, Object value, CompositeObject argument) throws NoSuchFieldException {
    if (name.startsWith("in.")) {
      name = name.substring("in.".length());
    }
    if (name.indexOf(".") < 0) {
      argument.set(name, value);
    } else {
      String newArgumentName = name.substring(0, name.indexOf("."));
      String RemainingName = name.substring(name.indexOf(".") + 1, name.length());
      setVariable(RemainingName, value, (CompositeObject) argument.get(newArgumentName));
    }
  }

  public void ivyPrint(String msg, IIvyScriptContext cont) throws IvyScriptException, PersistencyException {
    String tmpStr = new String();
    for (char c : msg.toCharArray()) {
      if (c != "\"".toCharArray()[0])
        tmpStr += c;
    }
    // this.executeIvyScript(cont, "ivy.log.info(\"" + tmpStr + "\")");
  }
}