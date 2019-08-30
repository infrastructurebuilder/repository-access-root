package org.infrastructurebuilder.util.artifacts.azuredevops;

public interface ADSReleaseDefinitionApprovalStep {

  /**
   * @return  Gets and sets the approver.
   */
  ADSIdentityRef getApprover();

  /**
   *
   * @return  ID of the approval or deploy step.
   */
  int getId();

  /**
   *
   * @return  Indicates whether the approval automated.
   */
  boolean isAutomated();

  /**
   *
   * @return  Indicates whether the approval notification set.
   */
  boolean isNotificationOn();

  /**
   *
   * @return  Gets or sets the rank of approval step.
   */
  int getRank();

}
