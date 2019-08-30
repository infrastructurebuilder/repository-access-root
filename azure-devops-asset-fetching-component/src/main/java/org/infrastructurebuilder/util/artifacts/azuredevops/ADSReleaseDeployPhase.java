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
