package org.infrastructurebuilder.util.artifacts.azuredevops;

import java.util.List;

public interface ADSReleaseDefinitionGate {
  List<ADSWorkflowTask> getTasks();
}
