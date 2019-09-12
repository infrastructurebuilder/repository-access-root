
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

public interface ADSReleaseDefinitionGatesOptions {
  /**
   *
   * @return  Gets or sets as the gates enabled or not.
   */
  boolean isEnabled();

  /**
   *
   * @returnGets or sets the minimum duration for steady results after a successful gates evaluation.
   */
  int getMinimumSuccessDuration();

  /**
   *
   * @returnGets or sets the time between re-evaluation of gates.
   */
  int getSamplingInterval();

  /**
   *
   * @returnGets or sets the delay before evaluation.
   */
  int getStabilizationTime();

  /**
   *
   * @return Gets or sets the timeout after which gates fail.
   */
  int getTimeout();
}
