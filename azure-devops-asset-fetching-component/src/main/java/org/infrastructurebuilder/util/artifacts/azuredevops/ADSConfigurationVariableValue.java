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

public interface ADSConfigurationVariableValue {

  /**
   *
   * @return  Gets and sets if a variable can be overridden at deployment time or not.
   */
  boolean isAllowOverride();

  /**
   *
   * @return  Gets or sets as variable is secret or not.
   */
  boolean isSecret();

  /**
   *
   * @return  Gets and sets value of the configuration variable.
   */
  String getValue();
}
