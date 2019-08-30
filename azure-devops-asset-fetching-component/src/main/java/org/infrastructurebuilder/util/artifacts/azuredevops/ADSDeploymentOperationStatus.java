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
