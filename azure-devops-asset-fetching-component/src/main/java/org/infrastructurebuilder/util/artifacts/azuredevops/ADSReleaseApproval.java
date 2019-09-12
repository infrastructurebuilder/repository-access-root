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

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * getName in this case will return nothing
 * @author mykel.alvis
 *
 */
public interface ADSReleaseApproval extends ADSNamedAndOwned<ADSRelease> {
  /**
   * @return   Gets or sets the type of approval.
   */
  ADSApprovalType getApprovalType();

  /**
   *
   * @return  Gets the identity who approved.
   */
  ADSIdentityRef getApprovedBy();

  /**
   *
   * @return  Gets or sets the identity who <i>should</i> approve.
   */
  ADSIdentityRef getApprover();

  /**
   *
   * @return  Gets or sets attempt which specifies as which deployment attempt it belongs.
   */
  int getAttempt();

  /**
   *
   * @return  Gets or sets comments for approval.
   */
  default Optional<String> getComments() {
    return getDescription();
  }

  /**
   *
   * @return  Gets date on which it got created.
   */
  Date getCreatedOn();

  /**
   * @return   Gets history which specifies all approvals associated with this approval.
   */
  List<ADSReleaseApprovalHistory> getHistory();

  /**
   * This is really an int, but we're using a string
   * @return  Gets the unique identifier of this field.
  String getId();
   */

  /**
   *
   * @return  Gets or sets as approval is automated or not.
   */
  boolean isAutomated();

  /**
   *
   * @return  Gets date on which it got modified.
   */
  Optional<Date> getModifiedOn();

  /**
   *
   * @return  Gets or sets rank which specifies the order of the approval. e.g. Same rank denotes parallel approval.
   */
  int getRank();

  /**
   *
   * @return  Gets releaseReference which specifies the reference of the release to which this approval is associated.
  ADSRelease getRelease(); -> getOwner()
   */
  default ADSRelease getRelease() {
    return getOwner();
  }

  /**
   *
   * @return  Gets releaseDefinitionReference which specifies the reference of the release definition to which this approval is associated.
   */
  ADSReleaseDefinitionShallowReference getReleaseDefinition();

  /**
   *
   * @return  Gets releaseEnvironmentReference which specifies the reference of the release environment to which this approval is associated.
   */
  ADSReleaseEnvironment getReleaseEnvironment();

  /**
   *
   * @return  Gets the revision number.
   */
  int getRevision();

  /**
   *
   * @return  Gets or sets the status of the approval.
   */
  ADSApprovalStatus getStatus();

}
