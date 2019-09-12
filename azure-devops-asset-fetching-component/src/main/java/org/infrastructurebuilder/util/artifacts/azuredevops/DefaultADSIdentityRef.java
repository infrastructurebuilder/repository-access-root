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
import java.util.Map;
import java.util.Optional;

import org.infrastructurebuilder.IBException;
import org.infrastructurebuilder.util.IBUtils;
import org.json.JSONObject;

public class DefaultADSIdentityRef extends AbstractIdentifiedNamedDescribedUrldOwned<ADSOrg> implements ADSIdentityRef {
  private final Map<String, String> links;

  public DefaultADSIdentityRef(ADSOrg owner, JSONObject src) {
    super(owner, src.getString("id"), src.getString("name"),
        IBException.cet.withReturningTranslation(() -> new URL(src.getString("url"))),
        Optional.ofNullable(src.getString("description")), owner.getClient());
    this.links = IBUtils.splitToMap(src.getJSONObject("_links"));
  }

  @Override
  public Map<String, String> getLinks() {
    return this.links;
  }

  @Override
  public String getDirectoryAlias() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Optional<URL> getImageUrl() {
    // TODO Auto-generated method stub
    return Optional.empty();
  }

  @Override
  public boolean isInactive() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isAadIdentity() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isContainer() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isDeletedInOrigin() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Optional<URL> getProfileUrl() {
    // TODO Auto-generated method stub
    return Optional.empty();
  }

  @Override
  public Optional<String> getUniqueName() {
    // TODO Auto-generated method stub
    return null;
  }
}
