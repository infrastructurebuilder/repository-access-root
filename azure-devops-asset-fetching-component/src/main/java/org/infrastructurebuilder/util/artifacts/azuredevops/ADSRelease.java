package org.infrastructurebuilder.util.artifacts.azuredevops;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;

public interface ADSRelease extends ADSNamedAndOwned<ADSRepo> {

  /**
   * @return non-null List of ADSArtifact instances
   */
  List<ADSArtifact> getArtifacts();

  Optional<String> getComment();

  Date getCreatedOn();

  int getDefinitionSnapshotRevision();

  List<ADSReleaseEnvironment> getEnvironments();

  boolean getKeepForever();

  URL getLogsContainerUrl();

  ADSIdentityRef getModifiedBy();

  Date getModifiedOn();

  String getPoolName();

  ADSProject getProjectReference();

  JSONObject getPropertiesCollection(); // FIXME PropertiesCollection is a real thing

  ADSReleaseReason getReleaseReason();

  ADSReleaseDefinitionShallowReference getReleaseDefinition();

  int getReleaseDefinitionRevision();

  String getReleaseNameFormat();

  ADSReleaseStatus getStatus();

  List<String> getTags();

  String getTriggeringArtifactAlias();

  List<ADSVariableGroup> getVariableGroups();

  Map<String, ADSConfigurationVariableValue> getVariables();

}
