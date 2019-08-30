package org.infrastructurebuilder.util.artifacts.azuredevops;

public enum ADSDeploymentReason {
  AUTOMATED, //   The deployment reason is automated.

  MANUAL, //   The deployment reason is manual.

  NONE, //   The deployment reason is none.

  REDEPLOYTRIGGER, //   The deployment reason is RedeployTrigger.

  SCHEDULED //   The deployment reason is scheduled.
}
