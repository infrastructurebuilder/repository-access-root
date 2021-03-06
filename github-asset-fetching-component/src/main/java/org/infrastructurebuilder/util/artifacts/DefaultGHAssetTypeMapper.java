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

import javax.inject.Named;
import javax.inject.Singleton;

import org.kohsuke.github.GHAsset;

@Named(DefaultGHAssetTypeMapper.GITHUB)
@Singleton
public class DefaultGHAssetTypeMapper implements GHAssetTypeMapper {

  static final String GITHUB = "github";

  @Override
  public Boolean apply(GAV t, GHAsset u) {
    return (
        // artifactId
        u.getOwner().getName().equals(t.getArtifactId())
        && //
        t.getVersion().map(v -> u.getUrl().toExternalForm().contains(v)).orElse(false)
        );
  }

  @Override
  public String getId() {
    return GITHUB;
  }

}
