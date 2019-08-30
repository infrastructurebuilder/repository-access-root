package org.infrastructurebuilder.util.artifacts.azuredevops;

public interface ADSWorkflowTaskReference {

  /**
   *
   * @return  Task identifier.
   */
  String getId();

  /**
   *
   * @return  Name of the task.
   */
  String getName();

  /**
   *
   * @return  Version of the task.
   */
  String getVersion();
}
