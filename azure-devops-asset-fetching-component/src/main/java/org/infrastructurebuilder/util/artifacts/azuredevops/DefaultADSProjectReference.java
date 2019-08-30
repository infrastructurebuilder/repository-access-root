package org.infrastructurebuilder.util.artifacts.azuredevops;

import org.json.JSONObject;

public class DefaultADSProjectReference implements ADSProjectReference {

  private final String name;
  private final String id;

  public DefaultADSProjectReference(JSONObject src) {
    this.id = src.getString("id");
    this.name = src.getString("name");
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

}
