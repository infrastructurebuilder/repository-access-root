package org.infrastructurebuilder.util.artifacts.azuredevops;

public enum ADSDeployPhaseType {
  AGENTBASEDDEPLOYMENT, //   Phase type which contains tasks executed on agent.

  DEPLOYMENTGATES, //   Phase type which contains tasks which acts as Gates for the deployment to go forward.

  MACHINEGROUPBASEDDEPLOYMENT, //   Phase type which contains tasks executed on deployment group machines.

  RUNONSERVER, //   Phase type which contains tasks executed by server.

  UNDEFINED //   Phase type not defined. Don't use this.
}
