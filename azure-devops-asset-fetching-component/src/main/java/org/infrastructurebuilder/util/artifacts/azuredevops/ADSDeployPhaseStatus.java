package org.infrastructurebuilder.util.artifacts.azuredevops;

public enum ADSDeployPhaseStatus {
  CANCELED, //   Phase execution canceled.

  CANCELLING, //   Phase is in cancelling state.

  FAILED, //   Phase execution failed.

  INPROGRESS, //   Phase execution in progress.

  NOTSTARTED, //   Phase execution not started.

  PARTIALLYSUCCEEDED, //   Phase execution partially succeeded.

  SKIPPED, //   Phase execution skipped.

  SUCCEEDED, //   Phase execution succeeded.

  UNDEFINED //   Phase status not set.
}
