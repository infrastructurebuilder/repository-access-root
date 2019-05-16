package org.infrastructurebuilder.util.artifacts;
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

//package org.infrastructurebuilder.util.artifacts;
//
//import java.util.TreeMap;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//import org.infrastructurebuilder.util.artifacts.ArtifactServices;
//import org.infrastructurebuilder.util.artifacts.GAV;
//import org.infrastructurebuilder.util.artifacts.model.ArtifactServicesConfiguration;


// FIXME IBRAManager is not functional yet
//public class IBRAManager {
//
//  private ArtifactServicesConfiguration configuration;
//
//  public IBRAManager() {
//    this(null);
//  }
//
//  public IBRAManager(final ArtifactServicesConfiguration defaultValue) {
//    setConfiguration(defaultValue);
//  }
//
//  public final ArtifactServices getArtifactServicesForArtifactAndScope(final GAV gav, final String scope) {
//    final TreeMap<Integer, ArtifactServices> tmap = new TreeMap<>();
//
//    configuration.getInstances()
//        .stream()
//        .collect(Collectors.toMap(k -> k.getWeight(), Function.identity()));
//
//    // TODO Map config instances into tMap
//
//    return tmap.entrySet().stream().max((l, r) -> {
//      return l.getKey().compareTo(r.getKey());
//    }).get().getValue();
//  }
//
//  public void setConfiguration(final ArtifactServicesConfiguration configuration) {
//    this.configuration = configuration;
//  }
//
//}
