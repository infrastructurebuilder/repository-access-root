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
import static org.infrastructurebuilder.util.IBUtils.parseISODateTime;
import static org.infrastructurebuilder.util.IBUtils.reURL;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.json.JSONObject;

public class DefaultADSRelease extends AbstractIdentifiedNamedDescribedUrldOwned<ADSRepo> implements ADSRelease {

  private List<ADSArtifact> artifacts;
  private final Optional<String> comment;
  private final Date createdOn;
  private final int definitionSnapshotRevision;
  private List<ADSReleaseEnvironment> environments;

  public DefaultADSRelease(ADSRepo owner, JSONObject src) {
    super(owner, new Integer(src.getInt("id")).toString(), src.getString("name"), reURL(src.getString("url")),
        ofNullable(src.getString("description")), owner.getClient());
    this.comment = ofNullable(src.optString("comment",null));
    this.createdOn = parseISODateTime.apply(src.getString("createdOn"));
    this.definitionSnapshotRevision  = src.getInt("definitionSnapshotRevision");

  }

  @Override
  public List<ADSArtifact> getArtifacts() {
    if (this.artifacts == null) {
      // TODO Fetch the artifacts
    }
    return this.artifacts;
  }

  @Override
  public Optional<String> getComment() {
    // TODO Auto-generated method stub
    return comment;
  }

  @Override
  public Date getCreatedOn() {
    // TODO Auto-generated method stub
    return createdOn;
  }

  @Override
  public int getDefinitionSnapshotRevision() {
    // TODO Auto-generated method stub
    return definitionSnapshotRevision;
  }

  @Override
  public List<ADSReleaseEnvironment> getEnvironments() {
    if (this.environments == null) {

    }
    // TODO Auto-generated method stub
    return this.environments;
  }

  @Override
  public boolean getKeepForever() {
    // TODO Auto-generated method stub
    return keepForever;
  }

  @Override
  public URL getLogsContainerUrl() {
    // TODO Auto-generated method stub
    return logsContainerUrl;
  }

  @Override
  public ADSIdentityRef getModifiedBy() {
    // TODO Auto-generated method stub
    return modifiedBy;
  }

  @Override
  public Date getModifiedOn() {
    // TODO Auto-generated method stub
    return modifiedOn;
  }

  @Override
  public String getPoolName() {
    // TODO Auto-generated method stub
    return poolName;
  }

  @Override
  public ADSProject getProjectReference() {
    // TODO Auto-generated method stub
    return projectReference;
  }

  @Override
  public JSONObject getPropertiesCollection() {
    // TODO Auto-generated method stub
    return propertiesCollection;
  }

  @Override
  public ADSReleaseReason getReleaseReason() {
    // TODO Auto-generated method stub
    return releaseReason;
  }

  @Override
  public ADSReleaseDefinitionShallowReference getReleaseDefinition() {
    // TODO Auto-generated method stub
    return releaseDefinition;
  }

  @Override
  public int getReleaseDefinitionRevision() {
    // TODO Auto-generated method stub
    return releaseDefinitionRevision;
  }

  @Override
  public String getReleaseNameFormat() {
    // TODO Auto-generated method stub
    return releaseNameFormat;
  }

  @Override
  public ADSReleaseStatus getStatus() {
    // TODO Auto-generated method stub
    return status;
  }

  @Override
  public List<String> getTags() {
    // TODO Auto-generated method stub
    return tags;
  }

  @Override
  public String getTriggeringArtifactAlias() {
    // TODO Auto-generated method stub
    return triggeringArtifactAlias;
  }

  @Override
  public List<ADSVariableGroup> getVariableGroups() {
    // TODO Auto-generated method stub
    return variableGroups;
  }

  @Override
  public Map<String, ADSConfigurationVariableValue> getVariables() {
    // TODO Auto-generated method stub
    return variables;
  }

}
