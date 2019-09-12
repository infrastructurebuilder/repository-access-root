
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

public interface ADSApprovalOptions {
  /**
  autoTriggeredAndPreviousEnvironmentApprovedCanBeSkipped
  boolean
  Specify whether the approval can be skipped if the same approver approved the previous stage.
  **/
  boolean isAutoTriggeredAndPreviousEnvironmentApprovedCanBeSkipped();

  /**
   * enforceIdentityRevalidation
  boolean
  Specify whether revalidate identity of approver before completing the approval.
  **/
  boolean isEnforceIdentityRevalidation();

  /**
  executionOrder
  ApprovalExecutionOrder
  Approvals execution order.
  **/
  ADSApprovalExecutionOrder getExecutionOrder();

  /**
  releaseCreatorCanBeApprover
  boolean
  Specify whether the user requesting a release or deployment should allow to approver.
  **/
  boolean isReleaseCreatorCanBeApprover();

  /**
  requiredApproverCount
  integer
  The number of approvals required to move release forward. '0' means all approvals required.
  **/
  int getRequiredApproverCount();

  /**
  timeoutInMinutes
  integer
  Approval timeout. Approval default timeout is 30 days. Maximum allowed timeout is 365 days. '0' means default timeout i.e 30 days.
  **/
  int getTimeoutInMinutes();

}
