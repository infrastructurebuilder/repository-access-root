
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

import org.infrastructurebuilder.util.IBUtils;
import org.json.JSONObject;

public class DefaultADSReleaseDefinition implements ADSReleaseDefinitionShallowReference {

  private final Optional<JSONObject> _links;
  private final int id;
  private final String name;
  private final String path;
  private final ADSProjectReference projectReference;
  private final URL url;

  public DefaultADSReleaseDefinition(JSONObject src) {
    this._links = Optional.ofNullable(src.optJSONObject("_links"));
    this.id = src.getInt("id");
    this.name = src.getString("name");
    this.path = src.getString("path");
    this.url = IBUtils.reURL(src.getString("url"));
    this.projectReference = new DefaultADSProjectReference(src.getJSONObject("projectReference"));
  }

  @Override
  public Optional<JSONObject> get_Links() {
    return _links;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getPath() {
    return path;
  }

  @Override
  public ADSProjectReference getProjectReference() {
    return projectReference;
  }

  @Override
  public URL getUrl() {
    return url;
  }

}
