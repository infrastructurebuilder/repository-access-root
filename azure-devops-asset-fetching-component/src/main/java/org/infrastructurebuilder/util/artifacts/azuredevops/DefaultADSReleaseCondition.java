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
