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
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.infrastructurebuilder.util.IBUtils.asStringStream;
import static org.infrastructurebuilder.util.IBUtils.reURL;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.infrastructurebuilder.util.IBUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class DefaultADSRepo extends AbstractIdentifiedNamedDescribedUrldOwned<ADSProject> implements ADSRepo {

  private Map<String, DefaultADSRelease> releases; // Lazy load releases
  private final URL sshUrl;
  private final boolean fork;
  private final URL remoteUrl;
  private final Optional<ADSRepo> parentRepository;
  private final List<URL> validRemoteUrls;
  private final URL webUrl;
  private final String defaultBranch;

  public DefaultADSRepo(ADSProject owner, JSONObject src) {
    super(owner, src.getString("id"), src.getString("name"), reURL(src.getString("url")),
        ofNullable(src.getString("description")), owner.getClient());
    this.fork = src.getBoolean("isFork");
    this.defaultBranch = src.optString("defaultBranch", null);
    this.sshUrl = reURL(src.optString("sshUrl", null));
    this.remoteUrl = reURL(src.optString("remoteUrl", null));
    this.validRemoteUrls = asStringStream(ofNullable(src.optJSONArray("validRemoteUrls")).orElse(new JSONArray()))
        .map(IBUtils::reURL).collect(toList());
    this.webUrl = reURL(src.optString("webUrl", null));
    this.parentRepository = null;// TODO Get the parent repo
  }

  public Map<String, ? extends ADSRelease> getReleases() {
    if (this.releases == null) {
      try {
        this.releases = getOwner().getOwner().call(this.getId(), "release/releases", Optional.empty())
            .map(resp -> IBUtils.asJSONObjectStream(resp.getJSONArray("values"))
                .map(j2 -> new DefaultADSRelease(this, j2))
                .collect(Collectors.toMap(k -> k.getId(), Function.identity())))
            .orElse(Collections.emptyMap());

      } catch (IOException e) {
        // FIXME Do something about exceptions
        e.printStackTrace();
        return Collections.emptyMap(); // Don't set the value if exception
      }
    }
    return this.releases;
  }

  @Override
  public ADSRelease getReleaseByTagName(String tag) {
    return getReleases().get(requireNonNull(tag));
  }

  @Override
  public URL getSSHUrl() {
    return this.sshUrl;
  }

  @Override
  public boolean isFork() {
    return this.fork;
  }

  @Override
  public URL getRemoteUrl() {
    return this.remoteUrl;
  }

  @Override
  public Optional<ADSRepo> getParentRepository() {
    return this.parentRepository;
  }

  @Override
  public List<URL> getValidRemoteUrls() {
    return this.validRemoteUrls;
  }

  @Override
  public URL getWebUrl() {
    return this.webUrl;
  }

  @Override
  public String getDefaultBranch() {
    return this.defaultBranch;
  }

}
