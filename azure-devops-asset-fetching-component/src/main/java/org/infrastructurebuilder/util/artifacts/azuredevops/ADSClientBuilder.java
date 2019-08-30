package org.infrastructurebuilder.util.artifacts.azuredevops;

import java.util.Optional;
import java.util.Properties;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.infrastructurebuilder.util.auth.azuredevops.AzureDevopsAuthenticationProducer.*;

import org.infrastructurebuilder.util.ProxyProxy;
import org.kohsuke.github.HttpConnector;

public class ADSClientBuilder {

  public static ADSClientBuilder fromProperties(Properties props) {
    return new ADSClientBuilder(props);
  }

  private final Properties properties;
  private final Optional<String> tfsServer;
  private Optional<String> tfsApiVersion;
  private HttpConnector connector;
  private final Optional<String> tfsUsername;
  private final Optional<String> tfsOauth;
  private Optional<ProxyProxy> proxy = empty();

  public ADSClientBuilder withConnector(HttpConnector connector) {
    this.connector = requireNonNull(connector);
    return this;
  }

  public ADSClientBuilder withProxy(ProxyProxy proxy) {
    this.proxy = ofNullable(proxy);
    return this;
  }

  private ADSClientBuilder(Properties props) {
    this.properties = requireNonNull(props);
    this.tfsUsername = ofNullable(properties.getProperty(TFS_LOGIN, null));
    this.tfsOauth = ofNullable(properties.getProperty(TFS_OUATH, null));
    this.tfsServer = ofNullable(properties.getProperty(TFS_ENDPOINT, null));
    this.tfsApiVersion = empty();

  }

  public ADSClient build() {
    return buildPrivateClient();
  }

  private ADSClient buildPrivateClient() {
    return new DefaultADSClient(this.tfsServer, this.tfsApiVersion, this.tfsUsername, this.tfsOauth, this.proxy);
  }

}
