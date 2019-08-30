package org.infrastructurebuilder.util.artifacts.azuredevops;

import java.net.URL;
import java.util.Optional;

import org.infrastructurebuilder.util.IBUtils;
import org.json.JSONObject;

public class DefaultADSReleaseDefinition implements ADSReleaseDefinitionShallowReference {

  private final Optional<JSONObject> _links;
  private final int id;
  private final String name;
  private final String path;
  private final ADSProjectReference projectReference;
  private final URL url;

  public DefaultADSReleaseDefinition(JSONObject src) {
    this._links = Optional.ofNullable(src.optJSONObject("_links"));
    this.id = src.getInt("id");
    this.name = src.getString("name");
    this.path = src.getString("path");
    this.url = IBUtils.reURL(src.getString("url"));
    this.projectReference = new DefaultADSProjectReference(src.getJSONObject("projectReference"));
  }

  @Override
  public Optional<JSONObject> get_Links() {
    return _links;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getPath() {
    return path;
  }

  @Override
  public ADSProjectReference getProjectReference() {
    return projectReference;
  }

  @Override
  public URL getUrl() {
    return url;
  }

}
