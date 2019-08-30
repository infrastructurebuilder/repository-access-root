package org.infrastructurebuilder.util.artifacts.azuredevops;

import java.net.URL;
import java.util.Optional;

import org.json.JSONObject;

public interface ADSReleaseDefinitionShallowReference {

  /**
   *
   * @return  Gets the links to related resources, APIs, and views for the release definition.
   */
  Optional<JSONObject> get_Links();

  /**
   *
   * @return  Gets the unique identifier of release definition.
   */
  int getId();

  /**
   *
   * @return  Gets or sets the name of the release definition.
   */
  String getName();

  /**
   *
   * @return      Gets or sets the path of the release definition.
   */
  String getPath();

  /**
   *
   * @return      Gets or sets project reference.
   */
  ADSProjectReference getProjectReference();

  /**
   *
   * @return      Gets the REST API url to access the release definition.
   */
  URL getUrl();
}
