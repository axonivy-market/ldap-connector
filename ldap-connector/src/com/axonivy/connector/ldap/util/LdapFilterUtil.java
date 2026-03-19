package com.axonivy.connector.ldap.util;

/**
 * Utility methods for building safe LDAP filter strings.
 *
 * <p>This connector is a library: it executes whatever filter the caller constructs.
 * If any part of that filter is derived from untrusted input (e.g. end-user form
 * fields), the caller <strong>must</strong> escape individual filter values before
 * concatenating them into a filter expression.</p>
 *
 * @see <a href="https://tools.ietf.org/html/rfc4515">RFC 4515 – LDAP String Representation of Search Filters</a>
 */
public class LdapFilterUtil {

  private LdapFilterUtil() {
    // utility class
  }

  /**
   * Escapes a single LDAP filter value according to RFC 4515.
   *
   * <p>Apply this method to every value that originates from untrusted input
   * before embedding it in a filter string, for example:</p>
   *
   * <pre>{@code
   * String safeUid = LdapFilterUtil.escapeLdapFilterValue(userInput);
   * String filter  = "(&(objectClass=person)(uid=" + safeUid + "))";
   * }</pre>
   *
   * <p>The following characters are percent-encoded per RFC 4515:</p>
   * <ul>
   *   <li>{@code \} (backslash) → {@code \5c}</li>
   *   <li>{@code *} (asterisk)  → {@code \2a}</li>
   *   <li>{@code (} (left paren) → {@code \28}</li>
   *   <li>{@code )} (right paren) → {@code \29}</li>
   *   <li>NUL (U+0000) → {@code \00}</li>
   * </ul>
   *
   * @param input the raw value to escape; {@code null} is treated as an empty string
   * @return the escaped value, safe for use as a filter assertion value
   */
  public static String escapeLdapFilterValue(String input) {
    if (input == null) {
      return "";
    }
    StringBuilder sb = new StringBuilder(input.length());
    for (char c : input.toCharArray()) {
      switch (c) {
        case '\\':
          sb.append("\\5c");
          break;
        case '*':
          sb.append("\\2a");
          break;
        case '(':
          sb.append("\\28");
          break;
        case ')':
          sb.append("\\29");
          break;
        case '\u0000':
          sb.append("\\00");
          break;
        default:
          sb.append(c);
          break;
      }
    }
    return sb.toString();
  }
}
