
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

import java.util.Objects;

import org.json.JSONObject;

public class DefaultADSReleaseCondition implements ADSReleaseCondition {

  private final String name;
  private final String value;
  private final boolean result;
  private final ADSReleaseConditionType conditionType;

  public DefaultADSReleaseCondition(JSONObject src) {
    this.name = Objects.requireNonNull(src).getString("name");
    this.value = src.getString("value");
    this.result = src.getBoolean("result");
    this.conditionType = ADSReleaseConditionType.valueOf(src.getString("conditionType").toUpperCase());
  }

  @Override
  public ADSReleaseConditionType getConditionType() {
    return this.conditionType;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public boolean getResult() {
    return this.result;
  }

  @Override
  public String getValue() {
    return this.value;
  }
}
