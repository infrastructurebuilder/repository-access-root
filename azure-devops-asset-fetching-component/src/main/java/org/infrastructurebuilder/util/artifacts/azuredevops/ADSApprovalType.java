package org.infrastructurebuilder.util.artifacts.azuredevops;

public enum ADSApprovalType {
  ALL, //   Indicates all approvals.

  POSTDEPLOY, //   Indicates the approvals which executed after deployment.

  PREDEPLOY, //     Indicates the approvals which executed before deployment.

  UNDEFINED //   Indicates the approval type does not set.
}
