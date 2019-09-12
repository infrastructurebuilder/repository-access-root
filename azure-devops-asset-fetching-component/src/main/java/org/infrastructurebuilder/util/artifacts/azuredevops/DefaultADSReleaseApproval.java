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

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.infrastructurebuilder.util.IBUtils;
import org.json.JSONObject;

public class DefaultADSReleaseApproval extends AbstractIdentifiedNamedDescribedUrldOwned<ADSRelease>
    implements ADSReleaseApproval {

  private final ADSApprovalType approvalType;
  private final ADSIdentityRef approvedBy;
  private final ADSIdentityRef approver;
  private final int attempt;
  private final Date createdOn;
  private final boolean isAutomated;
  private final Optional<Date> modifiedOn;
  private final int rank;

  public DefaultADSReleaseApproval(ADSRelease owner, JSONObject src) {
    super(owner, new Integer(src.getInt("id")).toString(), "[no given name]", IBUtils.reURL(src.getString("url")),
        Optional.ofNullable(src.optString("comments", null)), owner.getClient());
    ADSOrg org = owner.getOwner().getOwner().getOwner();
    this.approvalType = ADSApprovalType.valueOf(src.getString("approvalType").toUpperCase());
    this.approvedBy = new DefaultADSIdentityRef(org, src.getJSONObject("approvedBy"));
    this.approver = new DefaultADSIdentityRef(org, src.getJSONObject("approver"));
    this.attempt = src.optInt("attempt", 0);
    this.createdOn = IBUtils.parseISODateTime.apply(src.getString("createdOn"));
    this.isAutomated = src.getBoolean("isAutomated");
    this.modifiedOn = Optional.ofNullable(src.optString("modifiedOn", null)).map(IBUtils.parseISODateTime);
    this.rank = src.getInt("rank");
    this.releaseDefinition = new DefaultADSReleaseDefinition(src.getJSONObject("releaseDefinition"));
  }

  @Override
  public ADSApprovalType getApprovalType() {
    return approvalType;
  }

  @Override
  public ADSIdentityRef getApprovedBy() {
    return approvedBy;
  }

  @Override
  public ADSIdentityRef getApprover() {
    return approver;
  }

  @Override
  public int getAttempt() {
    return attempt;
  }

  @Override
  public Date getCreatedOn() {
    return createdOn;
  }

  @Override
  public List<ADSReleaseApprovalHistory> getHistory() {
    // TODO Auto-generated method stub
    return history;
  }

  @Override
  public boolean isAutomated() {
    // TODO Auto-generated method stub
    return isAutomated;
  }

  @Override
  public Optional<Date> getModifiedOn() {
    return modifiedOn;
  }

  @Override
  public int getRank() {
    // TODO Auto-generated method stub
    return rank;
  }

  @Override
  public ADSReleaseDefinitionShallowReference getReleaseDefinition() {
    // TODO Auto-generated method stub
    return releaseDefinition;
  }

  @Override
  public ADSReleaseEnvironment getReleaseEnvironment() {
    // TODO Auto-generated method stub
    return releaseEnvironment;
  }

  @Override
  public int getRevision() {
    // TODO Auto-generated method stub
    return revision;
  }

  @Override
  public ADSApprovalStatus getStatus() {
    // TODO Auto-generated method stub
    return status;
  }

}
