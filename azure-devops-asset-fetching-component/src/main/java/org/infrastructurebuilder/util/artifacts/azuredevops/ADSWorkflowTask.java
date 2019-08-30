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
