package org.infrastructurebuilder.util.artifacts.azuredevops;

import java.util.Date;

public interface ADSReleaseApprovalHistory {

  /**
   *
   * @return  Identity of the approver.
   */
  ADSIdentityRef getApprover();

  /**
   *
   * @return  Identity of the object who changed approval.
   */
  ADSIdentityRef getChangedBy();

  /**
   *
   * @return  Approval history comments.
   */
  String getComments();

  /**
   *
   * @return  Time when this approval created.
   */
  Date getCreatedOn();

  /**
   *
   * @return  Time when this approval modified.
   */
  Date getModifiedOn();

  /**
   *
   * @return  Approval history revision.
   */
  int getRevision();

}
