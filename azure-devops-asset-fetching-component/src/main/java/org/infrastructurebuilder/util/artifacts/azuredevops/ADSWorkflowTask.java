
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

import org.json.JSONObject;

public interface ADSWorkflowTask {

  /**
   * @return  Gets or sets as the task always run or not.
   */
  boolean isAlwaysRun();

  /**
   * @return  Gets or sets the task condition.
   */
  String getCondition();

  /**
   * @return  Gets or sets as the task continue run on error or not.
   */
  boolean isContinueOnError();

  /**
   * @return  Gets or sets the task definition type. Example:- 'Agent', DeploymentGroup', 'Server' or 'ServerGate'.
   */
  String getDefinitionType();

  /**
   * @return  Gets or sets as the task enabled or not.
   */
  boolean isEnabled();

  /**
   * @return  Gets or sets the task environment variables.
   */
  JSONObject getEnvironment();

  /**
   * @return  Gets or sets the task inputs.
   */
  JSONObject getInputs();

  /**
   * @return  Gets or sets the name of the task.
   */
  String getName();

  /**
   * @return  Gets or sets the task override inputs.
   */
  JSONObject getOverrideInputs();

  /**
   * @return  Gets or sets the reference name of the task.
   */
  String getRefName();

  /**
   * @return  Gets or sets the ID of the task.
   */
  String getTaskId();

  /**
   * @return  Gets or sets the task timeout.
   */
  int getTimeoutInMinues();

  /**
   * @return Gets or sets the version of the task.
   */
  String getVersion();

}
