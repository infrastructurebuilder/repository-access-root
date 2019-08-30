package org.infrastructurebuilder.util.artifacts.azuredevops;

public enum ADSDeploymentStatus {
  ALL, //   The deployment status is all.

  FAILED, //   The deployment status is failed.

  INPROGRESS, //   The deployment status is in progress.

  NOTDEPLOYED, //   The deployment status is not deployed.

  PARTIALLYSUCCEEDED, //   The deployment status is partiallysucceeded.

  SUCCEEDED, //   The deployment status is succeeded.

  UNDEFINED //   The deployment status is undefined.
}
