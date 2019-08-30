package org.infrastructurebuilder.util.artifacts.azuredevops;

public interface ADSTaskSourceDefinitionBase {
  String getAuthKey();

  String getEndpoint();

  String getKeySelector();

  String getSelector();

  String getTarget();
}
