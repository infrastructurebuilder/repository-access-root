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
import java.util.Date;
import java.util.Optional;

public interface ADSManualIntervention {

  /**
   *
   * @return  Gets or sets the identity who should approve.
   */
  ADSIdentityRef getApprover();

  /**
   *
   * @return  Gets or sets comments for approval.
   */
  String getComments();

  /**
   * @return  Gets date on which it got created.
   */
  Date getCreatedOn();

  /**
   *
   * @return  Gets the unique identifier for manual intervention.
   */
  int getId();

  /**
   * @return  Gets or sets instructions for approval.
   */
  String getInstructions();

  /**
   * @return  Gets date on which it got modified.
   */
  Optional<Date> getModifiedOn();

  /**
   * @return  Gets or sets the name.
   */
  String getName();

  /**
   *
   * @return  Gets releaseReference for manual intervention.
   */
  ADSRelease getRelease();

  /**
   * @return  Gets releaseDefinitionReference for manual intervention.
   */
  ADSReleaseDefinitionShallowReference getReleaseDefinition();

  /**
   * @return  Gets releaseEnvironmentReference for manual intervention.
   */
  ADSReleaseEnvironment getReleaseEnvironment();

  /**
   * @return  Gets or sets the status of the manual intervention.
   */
  ADSManualInterventionStatus getStatus();

  /**
   * @return  Get task instance identifier.
   */
  String getTaskInstanceId();

  /**
   *
   * @return  Gets url to access the manual intervention.
   */
  URL getUrl();
}
