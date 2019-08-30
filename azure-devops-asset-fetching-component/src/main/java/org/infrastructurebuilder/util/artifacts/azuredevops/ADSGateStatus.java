package org.infrastructurebuilder.util.artifacts.azuredevops;

public enum ADSGateStatus {
  CANCELED, //   The gate execution cancelled.

  FAILED, //   The gate execution failed.

  INPROGRESS, //   The gate is currently in progress.

  NONE, //   The gate does not have the status set.

  PENDING, //   The gate is in pending state.

  SUCCEEDED //   The gate completed successfully.
}
