
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

public interface ADSDeploymentAttempt {
  /**
   *
   * @return  Deployment attempt.
   */
  int getAttempt();

  /**
   * @return  ID of the deployment.
   */
  int getDeploymentId();

  /**
   * @return Specifies whether deployment has started or not.
   */
  boolean isHasStarted();


  /**
   * @return  ID of deployment.
   */
  int getId();

  /**
   * @return  All the issues related to the deployment.
   */
  List<ADSReleaseIssue> getIssues();

  /**
   * @return  Identity who last modified this deployment.
   */
  ADSIdentityRef getLastModifiedBy();

  /**
   * @return  Time when this deployment last modified.
   */
  Date getLastModifiedOn();

  /**
   * @return  Deployment operation status.
   */
  ADSDeploymentOperationStatus getOperationStatus();

  /**
   * @return  Post deployment gates that executed in this deployment.
   */
  ADSReleaseGates getPostDeploymentGates();

  /**
   * @return  Pre deployment gates that executed in this deployment.
   */
  ADSReleaseGates getPreDeploymentGates();

  /**
   *
   * @return  When this deployment queued on.
   */
  Date getQueuedOn();

  /**
   *
   * @return  Reason for the deployment.
   */
  ADSDeploymentReason getReason();

  /**
   * @return  List of release deployphases executed in this deployment.
   */
  List<ADSReleaseDeployPhase> getReleaseDeployPhases();

  /**
   * @return  Identity who requested this deployment.
   */
  ADSIdentityRef getRequestedBy();

  /**
   * @return  Identity for this deployment requested.
   */
  ADSIdentityRef getRequetedFor();

  /**
   * @return  status of the deployment.
   */
  ADSDeploymentStatus getStatus();
}
