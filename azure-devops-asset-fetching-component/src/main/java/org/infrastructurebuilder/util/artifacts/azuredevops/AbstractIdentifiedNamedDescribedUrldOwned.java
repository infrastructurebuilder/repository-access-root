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

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.util.Optional;

public abstract class AbstractIdentifiedNamedDescribedUrldOwned<O> {

  private final O owner;
  private final String name;
  private final ADSClient client;
  private final Optional<String> description;
  private final URL url;
  private final String id;

  public AbstractIdentifiedNamedDescribedUrldOwned(O owner, String id, String name, URL url, Optional<String> description,
      ADSClient client) {
    this.id = requireNonNull(id);
    this.owner = requireNonNull(owner);
    this.name = requireNonNull(name);
    this.client = requireNonNull(client);
    this.description = requireNonNull(description);
    this.url = requireNonNull(url);
  }

  public final String getName() {
    return this.name;
  }

  public final O getOwner() {
    return this.owner;
  }

  public ADSClient getClient() {
    return client;
  }

  public Optional<String> getDescription() {
    return description;
  }

  public URL getUrl() {
    return url;
  }

  public String getId() {
    return id;
  }
}
