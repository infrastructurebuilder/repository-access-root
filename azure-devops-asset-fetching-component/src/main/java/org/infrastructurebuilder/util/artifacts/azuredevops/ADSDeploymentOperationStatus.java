
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

public enum ADSDeploymentOperationStatus {
  ALL, //    The deployment operation status is all.

  APPROVED, //    The deployment operation status is approved.

  CANCELED, //    The deployment operation status is canceled.

  CANCELLING, //    The deployment operation status is cancelling.

  DEFERRED, //    The deployment operation status is deferred.

  EVALUATINGGATES, //  The deployment operation status is EvaluatingGates.

  GATEFAILED, //    The deployment operation status is GateFailed.

  MANUALINTERVENTIONPENDING, //    The deployment operation status is manualintervention pending.

  PENDING, //    The deployment operation status is pending.

  PHASECANCELED, //    The deployment operation status is phase canceled.

  PHASEFAILED, //    The deployment operation status is phase failed.

  PHASEINPROGRESS, //    The deployment operation status is phase in progress.

  PHASEPARTIALLYSUCCEEDED, //    The deployment operation status is phase partially succeeded.

  PHASESUCCEEDED, //    The deployment operation status is phase succeeded.

  QUEUED, //    The deployment operation status is queued.

  QUEUEDFORAGENT, //    The deployment operation status is queued for agent.

  QUEUEDFORPIPELINE, //    The deployment operation status is queued for pipeline.

  REJECTED, //    The deployment operation status is rejected.

  SCHEDULED, //    The deployment operation status is scheduled.

  UNDEFINED //    The deployment operation status is undefined.
}
