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

import java.util.Optional;

public interface ArtifactServicesFactory {
  /**
   * Create ArtifactServices directly from the settings provided. Local repo is
   * provided by Injected Settings
   *
   * @param serverId  get the Server entry from the injected SettingsSupplier
   *                  Settings.Servers in IBRAComponentFromSettings
   * @param mirrorId  get the Remote repo url from the injected SettingsSupplier
   *                  Settings.Mirrors in IBRAComponentFromSettings
   * @param normalize normalize snapshots
   * @return Configured ArtifactServices instance
   */
  ArtifactServices create(Optional<String> serverId, Optional<String> mirrorId, boolean normalize);

  /**
   * Create an {@link ArtifactServices} instance utilizing only the
   * already-resolved (i.e. offline) local artifact
   *
   * @param normalize Normalize snapshot versions
   * @return offline instance of {@link ArtifactServices}
   */
  ArtifactServices createLocal(boolean normalize);

}
