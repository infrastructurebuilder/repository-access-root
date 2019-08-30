package org.infrastructurebuilder.util.artifacts.azuredevops;

public interface ADSReleaseCondition {
  ADSReleaseConditionType getConditionType();

  String getName();

  boolean getResult();

  String getValue();

}
