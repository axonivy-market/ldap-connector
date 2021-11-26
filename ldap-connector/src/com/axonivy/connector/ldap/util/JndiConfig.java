package com.axonivy.connector.ldap.util;

import org.apache.commons.lang3.StringUtils;

import ch.ivyteam.naming.JndiProvider;

public class JndiConfig {

  public static final String AUTH_KIND_NONE = "none";
  public static final String AUTH_KIND_SIMPLE = "simple";

  private final JndiProvider provider;
  private final String url;
  private final String authenticationKind;
  private final String userName;
  private final String password;
  private final String connectionTimeout;
  private final boolean useSsl;
  private final boolean useLdapConnectionPool;

  private JndiConfig(JndiProvider provider, String url, String authenticationKind, String userName,
          String password, boolean useSsl, boolean useLdapConnectionPool, String connectionTimeout) {
    this.provider = provider;
    this.url = url;
    this.authenticationKind = authenticationKind;
    this.userName = userName;
    this.password = password;
    this.connectionTimeout = connectionTimeout;
    this.useSsl = useSsl;
    this.useLdapConnectionPool = useLdapConnectionPool;
  }

  public JndiProvider getProvider() {
    return provider;
  }

  public String getUrl() {
    return url;
  }

  public String getAuthenticationKind() {
    return authenticationKind;
  }

  public String getUserName() {
    return userName;
  }

  public String getPassword() {
    return password;
  }

  public boolean isUseSsl() {
    return useSsl;
  }

  public boolean isUseLdapConnectionPool() {
    return useLdapConnectionPool;
  }

  public static Builder create() {
    return new Builder();
  }

  public String getConnectionTimeout() {
    return connectionTimeout;
  }

  public static Builder create(JndiConfig config) {
    return new Builder()
            .authenticationKind(config.authenticationKind)
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
    private String connectionTimeout = "1000";
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

    public Builder connectionTimeout(String connectionTimeout) {
      this.connectionTimeout = StringUtils.defaultString(connectionTimeout);
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
              useLdapConnectionPool, connectionTimeout);
    }

  }

}
