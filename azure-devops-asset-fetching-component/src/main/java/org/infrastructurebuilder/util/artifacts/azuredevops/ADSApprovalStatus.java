package org.infrastructurebuilder.util.artifacts.azuredevops;

public enum ADSApprovalStatus {
  APPROVED, //  Indicates the approval is approved.

  CANCELED, //   Indicates the approval is canceled.

  PENDING, //   Indicates the approval is pending.

  REASSIGNED, //    Indicates the approval is reassigned.

  REJECTED,  //   Indicates the approval is rejected.

  SKIPPED, //   Indicates the approval is skipped.

  UNDEFINED //   Indicates the approval does not have the status set.
}
