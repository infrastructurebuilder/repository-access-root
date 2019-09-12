
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

import java.util.List;

public interface ADSDeployPhase {
  /**
   *
   * @return  Gets and sets the name of deploy phase.
   */
  String getName();

  /**
   *
   * @return  Indicates the deploy phase type.
   */
  ADSDeployPhaseType getPhaseType();

  /**
   *
   * @return  Gets and sets the rank of deploy phase.
   */
  int getRank();

  /**
   *
   * @return  Gets and sets the reference name of deploy phase.
   */
  String getRefName();

  /**
   *
   * @return  Gets and sets the workflow tasks for the deploy phase.
   */
  List<ADSWorkflowTask> getWorkflowTasks();
}
