package org.infrastructurebuilder.util.artifacts.azuredevops;

public enum ADSTaskStatus {
  CANCELED, //   The task execution canceled.

  FAILED, //   The task execution failed.

  FAILURE, //   The task execution failed.

  INPROGRESS, //   The task is currently in progress.

  PARTIALLYSUCCEEDED, //   The task execution partially succeeded.

  PENDING, //   The task is in pending status.

  SKIPPED, //   The task execution skipped.

  SUCCEEDED, //   The task completed successfully.

  SUCCESS, //   The task completed successfully.

  UNKNOWN //   The task does not have the status set.
}
