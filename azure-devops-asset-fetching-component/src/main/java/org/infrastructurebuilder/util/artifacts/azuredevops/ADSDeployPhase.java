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
