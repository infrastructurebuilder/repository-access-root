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
package org.infrastructurebuilder.util.artifacts;

import static java.util.Optional.ofNullable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;

import org.infrastructurebuilder.IBConstants;
import org.infrastructurebuilder.util.IBUtils;
import org.infrastructurebuilder.util.artifacts.azuredevops.ADSClient;
import org.infrastructurebuilder.util.artifacts.azuredevops.ADSClientBuilder;
import org.infrastructurebuilder.util.auth.IBAuthException;
import org.infrastructurebuilder.util.auth.IBAuthenticationProducerFactory;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.kohsuke.github.extras.OkHttp3Connector;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.OkUrlFactory;

@SuppressWarnings("deprecation")
@Named(IBConstants.AZUREDEVOPS)
public class DefaultADSClientSupplier implements ADSClientSupplier {

  final private Properties props;

  @Inject
  public DefaultADSClientSupplier(IBAuthenticationProducerFactory ibaf) {
    this.props = IBUtils.mapSS2Properties.apply(ibaf.getEnvironmentForTypes(Arrays.asList(getId())));
  }

  @Override
  public ADSClient get() {
    return IBAuthException.et.withReturningTranslation(() -> {
      Path cacheDir = Files.createTempDirectory("OKhttp3Cache");
      Cache cache = new Cache(cacheDir.toFile(), 10 * 1024 * 1024); // 10MB cache
      ADSClient adsClient = ADSClientBuilder.fromProperties(this.props)
          .withConnector(new OkHttp3Connector(new OkUrlFactory(new OkHttpClient.Builder().cache(cache).build())))
          // Get the critter
          .build();
      return adsClient;
    });
  }

  @Override
  public Optional<String> getToken() {
    return ofNullable(this.props.getProperty("oauth", null));
  }

  String getId() {
    return IBConstants.GITHUB;
  }
}
