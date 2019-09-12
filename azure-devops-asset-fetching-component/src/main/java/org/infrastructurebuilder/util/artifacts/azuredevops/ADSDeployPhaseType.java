/**
 * Copyright © 2019 admin (admin@infrastructurebuilder.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.infrastructurebuilder.util.artifacts.azuredevops;

public enum ADSDeployPhaseType {
  AGENTBASEDDEPLOYMENT, //   Phase type which contains tasks executed on agent.

  DEPLOYMENTGATES, //   Phase type which contains tasks which acts as Gates for the deployment to go forward.

  MACHINEGROUPBASEDDEPLOYMENT, //   Phase type which contains tasks executed on deployment group machines.

  RUNONSERVER, //   Phase type which contains tasks executed by server.

  UNDEFINED //   Phase type not defined. Don't use this.
}
