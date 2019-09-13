
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

import java.net.URL;
import java.util.Optional;

import org.json.JSONObject;

public interface ADSReleaseDefinitionShallowReference {

  /**
   *
   * @return  Gets the links to related resources, APIs, and views for the release definition.
   */
  Optional<JSONObject> get_Links();

  /**
   *
   * @return  Gets the unique identifier of release definition.
   */
  int getId();

  /**
   *
   * @return  Gets or sets the name of the release definition.
   */
  String getName();

  /**
   *
   * @return      Gets or sets the path of the release definition.
   */
  String getPath();

  /**
   *
   * @return      Gets or sets project reference.
   */
  ADSProjectReference getProjectReference();

  /**
   *
   * @return      Gets the REST API url to access the release definition.
   */
  URL getUrl();
}
