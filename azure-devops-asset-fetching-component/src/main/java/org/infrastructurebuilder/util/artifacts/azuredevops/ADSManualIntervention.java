package org.infrastructurebuilder.util.artifacts.azuredevops;

import java.net.URL;
import java.util.Date;
import java.util.Optional;

public interface ADSManualIntervention {

  /**
   *
   * @return  Gets or sets the identity who should approve.
   */
  ADSIdentityRef getApprover();

  /**
   *
   * @return  Gets or sets comments for approval.
   */
  String getComments();

  /**
   * @return  Gets date on which it got created.
   */
  Date getCreatedOn();

  /**
   *
   * @return  Gets the unique identifier for manual intervention.
   */
  int getId();

  /**
   * @return  Gets or sets instructions for approval.
   */
  String getInstructions();

  /**
   * @return  Gets date on which it got modified.
   */
  Optional<Date> getModifiedOn();

  /**
   * @return  Gets or sets the name.
   */
  String getName();

  /**
   *
   * @return  Gets releaseReference for manual intervention.
   */
  ADSRelease getRelease();

  /**
   * @return  Gets releaseDefinitionReference for manual intervention.
   */
  ADSReleaseDefinitionShallowReference getReleaseDefinition();

  /**
   * @return  Gets releaseEnvironmentReference for manual intervention.
   */
  ADSReleaseEnvironment getReleaseEnvironment();

  /**
   * @return  Gets or sets the status of the manual intervention.
   */
  ADSManualInterventionStatus getStatus();

  /**
   * @return  Get task instance identifier.
   */
  String getTaskInstanceId();

  /**
   *
   * @return  Gets url to access the manual intervention.
   */
  URL getUrl();
}
