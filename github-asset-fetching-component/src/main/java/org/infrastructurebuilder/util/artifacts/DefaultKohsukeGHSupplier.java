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

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Function;

import javax.inject.Inject;

import org.infrastructurebuilder.util.auth.IBAuthException;
import org.infrastructurebuilder.util.auth.IBAuthenticationProducerFactory;
import org.infrastructurebuilder.util.auth.kohsuke.KohsukeGHAuthenticationProducer;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

public class DefaultKohsukeGHSupplier implements KohsukeGHSupplier {
  public final Function<Map<String, String>, Properties> mapSS2Properties = (m) -> {
    Properties p = new Properties();
    Objects.requireNonNull(m).entrySet().stream().forEach(e -> p.setProperty(e.getKey(), e.getValue()));
    return p;
  };
  private final IBAuthenticationProducerFactory ibaf;

  @Inject
  public DefaultKohsukeGHSupplier(IBAuthenticationProducerFactory ibaf) {
    this.ibaf = Objects.requireNonNull(ibaf);
  }

  @Override
  public GitHub get() {
    // TODO Auto-generated method stub
    return IBAuthException.et
        .withReturningTranslation(() -> GitHubBuilder
            .fromProperties(mapSS2Properties
                .apply(ibaf.getEnvironmentForTypes(Arrays.asList(KohsukeGHAuthenticationProducer.KOHSUKE_TYPE))))
            .build());
  }

}
