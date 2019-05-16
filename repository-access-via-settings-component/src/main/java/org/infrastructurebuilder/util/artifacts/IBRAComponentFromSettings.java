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

import java.util.Objects;
import java.util.Optional;

import org.apache.maven.settings.Mirror;
import org.apache.maven.settings.Server;
import org.apache.maven.settings.Settings;
import org.infrastructurebuilder.utils.settings.SettingsSupplier;

public class IBRAComponentFromSettings implements IBRAFromSettingsFactory {

  private final Settings settings;

  public IBRAComponentFromSettings(final SettingsSupplier supplier) {
    settings = supplier.get();
  }

  @Override
  public ArtifactServices create(Optional<String> serverId, Optional<String> mirrorId, boolean normalize) {

    Server server = Objects.requireNonNull(serverId)
        .map(sid -> Optional.ofNullable(settings.getServer(sid)).orElse(new Server())).orElse(new Server());

    String remoteRepoUrl = Objects.requireNonNull(mirrorId)
        .map(mid -> (settings.getMirrors().stream().filter(m -> mid.equals(m.getId())).findFirst().map(Mirror::getUrl))
            .orElse(ArtifactServices.CENTRAL_REPO_URL))
        .orElse(ArtifactServices.CENTRAL_REPO_URL);

    String localRepo = settings.getLocalRepository();
    if (settings.isOffline()) {
      server = new Server();
      remoteRepoUrl = null;
    }
    return new DefaultRepositoryAccess(localRepo, server.getUsername(), server.getPassword(), remoteRepoUrl, normalize);
  }

  @Override
  public ArtifactServices createLocal(boolean normalize) {
    String localRepo = settings.getLocalRepository();
    return new DefaultRepositoryAccess(localRepo, null, null, null, normalize);
  }
}
