package org.infrastructurebuilder.util.artifacts.azuredevops;

import java.util.Date;
import java.util.List;

public interface ADSDeploymentAttempt {
  /**
   *
   * @return  Deployment attempt.
   */
  int getAttempt();

  /**
   * @return  ID of the deployment.
   */
  int getDeploymentId();

  /**
   * @return Specifies whether deployment has started or not.
   */
  boolean isHasStarted();


  /**
   * @return  ID of deployment.
   */
  int getId();

  /**
   * @return  All the issues related to the deployment.
   */
  List<ADSReleaseIssue> getIssues();

  /**
   * @return  Identity who last modified this deployment.
   */
  ADSIdentityRef getLastModifiedBy();

  /**
   * @return  Time when this deployment last modified.
   */
  Date getLastModifiedOn();

  /**
   * @return  Deployment operation status.
   */
  ADSDeploymentOperationStatus getOperationStatus();

  /**
   * @return  Post deployment gates that executed in this deployment.
   */
  ADSReleaseGates getPostDeploymentGates();

  /**
   * @return  Pre deployment gates that executed in this deployment.
   */
  ADSReleaseGates getPreDeploymentGates();

  /**
   *
   * @return  When this deployment queued on.
   */
  Date getQueuedOn();

  /**
   *
   * @return  Reason for the deployment.
   */
  ADSDeploymentReason getReason();

  /**
   * @return  List of release deployphases executed in this deployment.
   */
  List<ADSReleaseDeployPhase> getReleaseDeployPhases();

  /**
   * @return  Identity who requested this deployment.
   */
  ADSIdentityRef getRequestedBy();

  /**
   * @return  Identity for this deployment requested.
   */
  ADSIdentityRef getRequetedFor();

  /**
   * @return  status of the deployment.
   */
  ADSDeploymentStatus getStatus();
}
