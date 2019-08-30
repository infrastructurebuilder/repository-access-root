package org.infrastructurebuilder.util.artifacts.azuredevops;

public enum ADSConditionType {
  ARTIFACT, //  The condition type is artifact.

  ENVIRONMENTSTATE, //   The condition type is environment state.

  EVENT, //    The condition type is event.

  UNDEFINED //   The condition type is undefined.
}
