package com.axonivy.connector.ldap.util;

import org.apache.commons.lang3.StringUtils;

public class JndiConfig {

  private final String provider;
  private final String url;
  private final String userName;
  private final String password;
  private final String connectionTimeout;
  private final String referral;

  private JndiConfig(String provider, String url, String userName,
          String password, String connectionTimeout, String referral) {
    this.provider = provider;
    this.url = url;
    this.userName = userName;
    this.password = password;
    this.connectionTimeout = connectionTimeout;
    this.referral = referral;
  }

  public String getProvider() {
    return provider;
  }

  public String getUrl() {
    return url;
  }

  public String getUserName() {
    return userName;
  }

  public String getPassword() {
    return password;
  }

  public static Builder create() {
    return new Builder();
  }

  public String getConnectionTimeout() {
    return connectionTimeout;
  }

  public String getReferral() {
    return referral;
  }

  public static Builder create(JndiConfig config) {
    return new Builder()
            .password(config.password)
            .provider(config.provider)
            .url(config.url)
            .userName(config.userName)
            .connectionTimeout(config.connectionTimeout)
            .referral(config.referral);
  }

  @SuppressWarnings("hiding")
  public static final class Builder {
    private String provider = "";
    private String url = "";
    private String userName = "";
    private String password = "";
    private String connectionTimeout = "";
    private String referral = "";

    public Builder provider(String provider) {
      this.provider = StringUtils.defaultString(provider);
      return this;
    }

    public Builder url(String url) {
      this.url = StringUtils.defaultString(url);
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

    public Builder referral(String referral) {
      this.referral = StringUtils.defaultString(referral);
      return this;
    }

    public JndiConfig toJndiConfig() {
      return new JndiConfig(provider, url, userName, password,
              connectionTimeout, referral);
    }

  }

}
