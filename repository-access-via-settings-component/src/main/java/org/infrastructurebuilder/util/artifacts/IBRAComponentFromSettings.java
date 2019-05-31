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

import java.net.URL;
import java.util.Objects;
import java.util.Optional;

import org.infrastructurebuilder.util.MirrorProxy;
import org.infrastructurebuilder.util.ServerProxy;
import org.infrastructurebuilder.util.SettingsProxy;
import org.infrastructurebuilder.util.SettingsSupplier;

public class IBRAComponentFromSettings implements IBRAFromSettingsFactory {

  private final SettingsProxy settings;

  public IBRAComponentFromSettings(final SettingsSupplier supplier) {
    settings = supplier.get();
  }

  @Override
  public ArtifactServices create(Optional<String> serverId, Optional<String> mirrorId, boolean normalize) {

    ServerProxy server = Objects.requireNonNull(serverId).map(sid -> settings.getServer(sid).orElse(new ServerProxy()))
        .orElse(new ServerProxy());

    URL remoteRepoUrl = Objects.requireNonNull(mirrorId).flatMap(mid -> settings.getMirror(mid)).map(MirrorProxy::getUrl).orElse(ArtifactServices.CENTRAL_REPO_URL);
    String localRepo = settings.getLocalRepository().toAbsolutePath().toString();
    if (settings.isOffline()) {
      server = new ServerProxy();
      remoteRepoUrl = null;
    }
    return new DefaultRepositoryAccess(localRepo, server.getPrincipal().orElse(null), server.getSecret().orElse(null),
        remoteRepoUrl.toExternalForm(), normalize);
  }

  @Override
  public ArtifactServices createLocal(boolean normalize) {
    String localRepo = settings.getLocalRepository().toAbsolutePath().toString();
    return new DefaultRepositoryAccess(localRepo, null, null, null, normalize);
  }
}
