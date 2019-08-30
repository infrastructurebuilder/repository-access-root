package org.infrastructurebuilder.util.artifacts.azuredevops;

import java.util.List;

public interface ADSReleaseDefinitionGatesStep {
  /**
   * @return  Gets or sets the gates.
   */
  List<ADSReleaseDefinitionGate> getGates();

  /**
   *
   * @return  Gets or sets the gate options.
   */
  ADSReleaseDefinitionGatesOptions getGatesOptions();

  /**
   *
   * @return  ID of the ReleaseDefinitionGateStep.
   */
  int getId();
}
