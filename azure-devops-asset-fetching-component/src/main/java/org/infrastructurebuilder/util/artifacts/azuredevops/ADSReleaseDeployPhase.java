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

public interface ADSReleaseDeployPhase {
  /**
   *
   * @return  Deployment jobs of the phase.
   */
  List<ADSDeploymentJob> getDeploymentJobs();

  /**
   * @return  Phase execution error logs.
   */
  String getErrorLog();

  /**
   * @return  List of manual intervention tasks execution information in phase.
   */
  List<ADSManualIntervention> getManualInterventions();

  /**
   * @return  Name of the phase.
   */
  String getName();

  /**
   * @return  ID of the phase.
   */
  String getPhaseId();

  /**
   * @return   Type of the phase.
   */
  ADSDeployPhaseType getPhaseType();

  /**
   * @return  Rank of the phase.
   */
  int getRank();

  /**
   *
   * @return  Run Plan ID of the phase.
   */
  String getRunPlanId();

  /**
   * @return  Phase start time.
   */
  Date getStartedOn();

  /**
   * @return Status of the phase.
   */
  ADSDeployPhaseStatus getStatus();

}
