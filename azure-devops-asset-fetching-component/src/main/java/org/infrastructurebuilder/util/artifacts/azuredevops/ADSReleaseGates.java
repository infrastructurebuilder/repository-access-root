
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

public interface ADSReleaseGates {
  /**
   * @return  Contains the gates job details of each evaluation.
   */
  List<ADSDeploymentJob> getDeploymentJobs();

  /**
   *
   * @return  ID of release gates.
   */
  int getId();

  /**
   *
   * @return  List of ignored gates.
   */
  List<ADSIgnoredGate> getIgnoredGates();

  /**
   *
   * @return  Gates last modified time.
   */
  Date getLastModifiedOn();

  /**
   *
   * @return  Run plan ID of the gates.
   */
  String getRunPlanId();

  /**
   * @return  Gates stabilization completed date and time.
   */
  Optional<Date> getStabilizationCompletedOn();

  /**
   * @return  Gates evaluation started time.
   */
  Date getStartedOn();

  /**
   * @return  Status of release gates.
   */
  ADSGateStatus getStatus();

  /**
   * @return  Date and time at which all gates executed successfully.
   */
  Optional<Date> getSucceedingSince();
}
