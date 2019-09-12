/**
 * Copyright © 2019 admin (admin@infrastructurebuilder.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
