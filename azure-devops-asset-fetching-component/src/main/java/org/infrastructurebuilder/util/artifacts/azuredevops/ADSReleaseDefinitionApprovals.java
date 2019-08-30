package org.infrastructurebuilder.util.artifacts.azuredevops;

import java.util.List;

public interface ADSReleaseDefinitionApprovals {
  /**
   *
   * @return  Gets or sets the approval options.
   */
  ADSApprovalOptions getApprovalOptions();

  /**
   * @return   Gets or sets the approvals.
   */
  List<ADSReleaseDefinitionApprovalStep> getApprovals();
}
