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
