package org.infrastructurebuilder.util.artifacts.azuredevops;

import java.util.Map;

public interface ADSArtifact extends ADSNamedAndOwned<ADSRelease> {
  /**
  alias
  string
  Gets or sets alias.
  **/
  String getAlias();

  /**
  definitionReference
  <string,  ArtifactSourceReference>
  Gets or sets definition reference. e.g. {"project":{"id":"fed755ea-49c5-4399-acea-fd5b5aa90a6c","name":"myProject"},"definition":{"id":"1","name":"mybuildDefinition"},"connection":{"id":"1","name":"myConnection"}}.
  **/
  Map<String, ADSArtifactSourceReference> getDefinitionReference();

  /**
  isPrimary
  boolean
  Indicates whether artifact is primary or not.
  **/
  boolean isPrimary();

  /**
  isRetained
  boolean
  Indicates whether artifact is retained by release or not.
  **/
  boolean isRetained();

  /**
  type
  string
  Gets or sets type. It can have value as 'Build', 'Jenkins', 'GitHub', 'Nuget', 'Team Build (external)', 'ExternalTFSBuild', 'Git', 'TFVC', 'ExternalTfsXamlBuild'.
  **/
  ADSArtifactType getType();
}
