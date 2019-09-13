
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

public interface ADSReleaseApprovalHistory {

  /**
   *
   * @return  Identity of the approver.
   */
  ADSIdentityRef getApprover();

  /**
   *
   * @return  Identity of the object who changed approval.
   */
  ADSIdentityRef getChangedBy();

  /**
   *
   * @return  Approval history comments.
   */
  String getComments();

  /**
   *
   * @return  Time when this approval created.
   */
  Date getCreatedOn();

  /**
   *
   * @return  Time when this approval modified.
   */
  Date getModifiedOn();

  /**
   *
   * @return  Approval history revision.
   */
  int getRevision();

}
