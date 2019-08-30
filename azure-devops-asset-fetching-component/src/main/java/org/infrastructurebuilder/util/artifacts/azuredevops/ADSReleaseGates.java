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
