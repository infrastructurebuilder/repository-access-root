
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
import java.util.Map;

public interface ADSReleaseEnvironment extends ADSNamedAndOwned<ADSRelease> {
  List<ADSReleaseCondition> getConditions();

  Date getCreatedOn();

  int getDefintionEnvironmentId();

  /**
   *
   * deployPhasesSnapshot
  DeployPhase[]
      Gets list of deploy phases snapshot.
   */
  List<ADSDeployPhase> getDeployPhasesSnapshot();

  /**
   *
  deploySteps
  DeploymentAttempt[]
      Gets deploy steps.
   */
  List<ADSDeploymentAttempt> getDeploySteps();
  /**
  environmentOptions
  EnvironmentOptions
  Gets environment options.
  **/
  ADSEnvironmentOptions getEnvironmentOptions();
  /**
   *
  modifiedOn
  string
  Gets date on which it got modified.
  **/

  Date getModifiedOn();
  /**
   *
  nextScheduledUtcTime
  string
  Gets next scheduled UTC time.
  **/
  Date getNextScheduledUtcTime();

  /**
   *

  owner
  IdentityRef
  Gets the identity who is owner for release environment.
  **/
  ADSIdentityRef getOwnerIdentityRef();

  /**
   *

  postApprovalsSnapshot
  ReleaseDefinitionApprovals
  Gets list of post deploy approvals snapshot.
  **/

  ADSReleaseDefinitionApprovals getPostApprovalsSnapshot();

  /**
   *
  postDeployApprovals
  ReleaseApproval[]
  Gets list of post deploy approvals.
  **/

  List<ADSReleaseApproval> getPostDeployApprovals();


  /**
   *

  postDeploymentGatesSnapshot
  ReleaseDefinitionGatesStep
  Post deployment gates snapshot data.
  **/

  ADSReleaseDefinitionGatesStep getPostDeploymentGatesSnapshot();
  /**
   *

  preApprovalsSnapshot
  ReleaseDefinitionApprovals
  Gets list of pre deploy approvals snapshot.
  **/

  ADSReleaseDefinitionApprovals getPreApprovalsSnapshot();

  /**
   *

  preDeployApprovals
  ReleaseApproval[]
  Gets list of pre deploy approvals.
  **/

  List<ADSReleaseApproval> getPreDeployApprovals();

  /**
   *

  preDeploymentGatesSnapshot
  ReleaseDefinitionGatesStep
  Pre deployment gates snapshot data.
  **/

  ADSReleaseDefinitionGatesStep getPreDeploymentGatesSnapshot();

  /**
   *

  processParameters
  ProcessParameters
  Gets process parameters.
  **/

  ADSProcessParameters getProcessParameters();

  /**
   *

  rank
  integer
  Gets rank.
  **/

  int getRank();
  /**
   *

  release
  ReleaseShallowReference
  Gets release reference which specifies the reference of the release to which this release environment is associated.
  **/

  ADSRelease getRelease();
  /**
   *

  releaseCreatedBy
  IdentityRef
  Gets the identity who created release.
  **/

  ADSIdentityRef getReleaseCreatedBy();

  /**
   *

  releaseDefinition
  ReleaseDefinitionShallowReference
  Gets releaseDefinitionReference which specifies the reference of the release definition to which this release environment is associated.
  **/

  ADSReleaseDefinitionShallowReference getReleaseDefinition();

  /**
   *
  *****  This is referenced as "getId()"
  releaseId
  integer
  Gets release id.
  **/
  /**
   *

  scheduledDeploymentTime
  string
  Gets schedule deployment time of release environment.
  **/
  Date getScheduledDeploymentTime();
  /**
   *

  schedules
  ReleaseSchedule[]
  Gets list of schedules.
  **/
  List<ADSReleaseSchedule> getSchedules();
  /**
   *

  status
  EnvironmentStatus
  Gets environment status.
  **/
  ADSEnvironmentStatus getStatus();
  /**
   *

  timeToDeploy
  number
  Gets time to deploy.
  **/
  int getTimeToDeploy(); // FIXME This seems wrong
  /**
   *

  triggerReason
  string
  Gets trigger reason.
  **/
  String getTriggerReason();

  /**
   *

  variableGroups
  VariableGroup[]
  Gets the list of variable groups.
  **/

  List<ADSVariableGroup> getVariableGroups();
  /**
   *

  variables
  <string,  ConfigurationVariableValue>
  Gets the dictionary of variabl
   */
  Map<String, ADSConfigurationVariableValue> getVariables();
}
