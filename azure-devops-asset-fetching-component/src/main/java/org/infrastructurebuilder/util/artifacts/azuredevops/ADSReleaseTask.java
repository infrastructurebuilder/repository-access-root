package org.infrastructurebuilder.util.artifacts.azuredevops;

public interface ADSReleaseTask {
/**

Name    Type    Description
agentName
string
Agent name on which task executed.

finishTime
string
Finish time of the release task.

id
integer
ID of the release task.

issues
Issue[]
List of issues occurred while execution of task.

lineCount
integer
Number of lines log release task has.

logUrl
string
Log URL of the task.

name
string
Name of the task.

percentComplete
integer
Task execution complete precent.

rank
integer
Rank of the release task.

resultCode
string
Result code of the task.

startTime
string
ID of the release task.
**/

  /**
   * @return  Status of release task.
   */
  ADSTaskStatus getStatus();

  /**
   * @return  Workflow task reference.
   */
  ADSWorkflowTaskReference getTask();
/**

timelineRecordId
string
Timeline record ID of the release task.

 */
}
