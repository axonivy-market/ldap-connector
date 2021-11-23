package com.axonivy.connector.ldap.util;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;

import org.apache.commons.lang3.StringUtils;

import ch.ivyteam.naming.JndiProvider;

public class JndiConfig {
  public static final String AUTH_KIND_NONE = "none";
  public static final String AUTH_KIND_SIMPLE = "simple";

  private static final String LDAP_CONNECTION_POOL = "com.sun.jndi.ldap.connect.pool";

  private JndiProvider provider;
  private String url;
  private String authenticationKind;
  private String userName;
  private String password;
  private String defaultContext;
  private boolean useSsl;
  private boolean useLdapConnectionPool;

  private JndiConfig(JndiProvider provider, String url, String authenticationKind, String userName,
          String password, boolean useSsl, boolean useLdapConnectionPool, String defaultContext) {
    this.provider = provider;
    this.url = url;
    this.authenticationKind = authenticationKind;
    this.userName = userName;
    this.password = password;
    this.useSsl = useSsl;
    this.useLdapConnectionPool = useLdapConnectionPool;
    this.defaultContext = defaultContext;
  }

  public void setProvider(JndiProvider provider) {
    this.provider = provider;
  }

  public JndiProvider getProvider() {
    return provider;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public void setAuthenticationKind(String authenticationKind) {
    this.authenticationKind = authenticationKind;
  }

  public String getAuthenticationKind() {
    return authenticationKind;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserName() {
    return userName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public void setDefaultContext(String defaultContext) {
    this.defaultContext = defaultContext;
  }

  public String getDefaultContext() {
    return defaultContext;
  }

  public LdapName getDefaultContextName() throws InvalidNameException {
    if (StringUtils.isBlank(defaultContext)) {
      return new LdapName("");
    }
    return new LdapName(defaultContext);
  }

  public void setUseSsl(boolean useSsl) {
    this.useSsl = useSsl;
  }

  public boolean isUseSsl() {
    return useSsl;
  }

  public Hashtable<?, ?> getEnvironement() {
    return createEnvironment();
  }

  Hashtable<?, ?> createEnvironment() {
    Hashtable<String, Object> env = new Hashtable<String, Object>();
    env.put(Context.INITIAL_CONTEXT_FACTORY, provider.getProviderClass());
    env.put(Context.PROVIDER_URL, url);
    if (authenticationKind.equals(AUTH_KIND_NONE)) {
      env.put(Context.SECURITY_AUTHENTICATION, AUTH_KIND_NONE);
    } else if (authenticationKind.equals(AUTH_KIND_SIMPLE)) {
      env.put(Context.SECURITY_AUTHENTICATION, AUTH_KIND_SIMPLE);
      env.put(Context.SECURITY_PRINCIPAL, userName);
      env.put(Context.SECURITY_CREDENTIALS, password);
    } else {
      env.put(Context.SECURITY_AUTHENTICATION, AUTH_KIND_NONE);
    }

    if (useSsl) {
      env.put(Context.SECURITY_PROTOCOL, "ssl");
    }
    if (useLdapConnectionPool) {
      env.put(LDAP_CONNECTION_POOL, "true");
    }

    env.put(Context.REFERRAL, "follow");

    if (JndiProvider.ACTIVE_DIRECTORY.equals(this.provider)) {
      env.put(Context.URL_PKG_PREFIXES, "ch.ivyteam.naming");
    }
    return env;
  }

  @Override
  public boolean equals(Object obj) {
    JndiConfig jndiConfig;
    if (obj instanceof JndiConfig) {
      jndiConfig = (JndiConfig) obj;

      if (this == jndiConfig) {
        return true;
      }

      return ((url.equals(jndiConfig.url)) &&
              (provider.equals(jndiConfig.provider)) &&
              (authenticationKind.equals(jndiConfig.authenticationKind)) &&
              (defaultContext.equals(jndiConfig.defaultContext)) &&
              (userName.equals(jndiConfig.userName)) &&
              (password.equals(jndiConfig.password)) &&
              (useSsl == jndiConfig.useSsl));
    }
    return false;
  }

  @Override
  public String toString() {
    return "JndiConfig[url=" + url + ", "
            + "provider=" + provider + ", "
            + "authenticationKind=" + authenticationKind + ","
            + "useSSL=" + useSsl + ","
            + "defaultContext=" + defaultContext + "]";
  }

  @Override
  public int hashCode() {
    return getUrl().hashCode();
  }

  public boolean isUseLdapConnectionPool() {
    return useLdapConnectionPool;
  }

  public void setUseLdapConnectionPool(boolean _useLdapConnectionPool) {
    this.useLdapConnectionPool = _useLdapConnectionPool;
  }

  public static Builder create() {
    return new Builder();
  }

  public static Builder create(JndiConfig config) {
    return new Builder()
            .authenticationKind(config.authenticationKind)
            .defaultContext(config.defaultContext)
            .password(config.password)
            .provider(config.provider)
            .url(config.url)
            .useLdapConnectionPool(config.useLdapConnectionPool)
            .userName(config.userName)
            .useSsl(config.useSsl);
  }

  @SuppressWarnings("hiding")
  public static final class Builder {
    private JndiProvider provider = new JndiProvider(null, null);
    private String url = "";
    private String authenticationKind = "none";
    private String userName = "";
    private String password = "";
    private String defaultContext = "";
    private boolean useSsl = false;
    private boolean useLdapConnectionPool = false;

    public Builder provider(JndiProvider provider) {
      if (provider != null) {
        this.provider = provider;
      }
      return this;
    }

    public Builder url(String url) {
      this.url = StringUtils.defaultString(url);
      return this;
    }

    public Builder authenticationKind(String authenticationKind) {
      this.authenticationKind = StringUtils.defaultString(authenticationKind);
      return this;
    }

    public Builder userName(String userName) {
      this.userName = StringUtils.defaultString(userName);
      return this;
    }

    public Builder password(String password) {
      this.password = StringUtils.defaultString(password);
      return this;
    }

    public Builder defaultContext(String defaultContext) {
      this.defaultContext = StringUtils.defaultString(defaultContext);
      return this;
    }

    public Builder useSsl(boolean useSsl) {
      this.useSsl = useSsl;
      return this;
    }

    public Builder useLdapConnectionPool(boolean useLdapConnectionPool) {
      this.useLdapConnectionPool = useLdapConnectionPool;
      return this;
    }

    public JndiConfig toJndiConfig() {
      return new JndiConfig(provider, url, authenticationKind, userName, password, useSsl,
              useLdapConnectionPool, defaultContext);
    }

  }

}
