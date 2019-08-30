package org.infrastructurebuilder.util.artifacts.azuredevops;

public enum ADSManualInterventionStatus {
  APPROVED, //   The manual intervention is approved.

  CANCELED, //   The manual intervention is canceled.

  PENDING, //   The manual intervention is pending.

  REJECTED, //   The manual intervention is rejected.

  UNKNOWN  //   The manual intervention does not have the status set
}
