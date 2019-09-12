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

public enum ADSTaskStatus {
  CANCELED, //   The task execution canceled.

  FAILED, //   The task execution failed.

  FAILURE, //   The task execution failed.

  INPROGRESS, //   The task is currently in progress.

  PARTIALLYSUCCEEDED, //   The task execution partially succeeded.

  PENDING, //   The task is in pending status.

  SKIPPED, //   The task execution skipped.

  SUCCEEDED, //   The task completed successfully.

  SUCCESS, //   The task completed successfully.

  UNKNOWN //   The task does not have the status set.
}
