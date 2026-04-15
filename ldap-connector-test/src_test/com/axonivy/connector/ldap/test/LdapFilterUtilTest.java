package com.axonivy.connector.ldap.test;

import org.junit.jupiter.api.Test;

import com.axonivy.connector.ldap.util.LdapFilterUtil;

import static org.junit.jupiter.api.Assertions.*;

class LdapFilterUtilTest {

  @Test
  void testEscapeLdapFilterValueWithNullOrEmptyInput() {
    assertEquals("", LdapFilterUtil.escapeLdapFilterValue(null));
    assertEquals("", LdapFilterUtil.escapeLdapFilterValue(""));
  }

  @Test
  void testEscapeLdapFilterValue_noSpecialChars() {
    assertEquals("simpleText", LdapFilterUtil.escapeLdapFilterValue("simpleText"));
  }

  @Test
  void testEscapeLdapFilterValue_withSpecialChars() {
    assertEquals("abc\\5cdef", LdapFilterUtil.escapeLdapFilterValue("abc\\def"));
    assertEquals("abc\\2adef", LdapFilterUtil.escapeLdapFilterValue("abc*def"));
    assertEquals("abc\\28def", LdapFilterUtil.escapeLdapFilterValue("abc(def"));
    assertEquals("abc\\29def", LdapFilterUtil.escapeLdapFilterValue("abc)def"));
    assertEquals("abc\\00def", LdapFilterUtil.escapeLdapFilterValue("abc\0def"));
    String input = "a*b(c)d\\e\0f";
    String expected = "a\\2ab\\28c\\29d\\5ce\\00f";
    assertEquals(expected, LdapFilterUtil.escapeLdapFilterValue(input));
    input = "\\*()\0";
    expected = "\\5c\\2a\\28\\29\\00";
    assertEquals(expected, LdapFilterUtil.escapeLdapFilterValue(input));
  }
}
