package org.infrastructurebuilder.util.artifacts.azuredevops;

import java.net.URL;
import java.util.Map;

import org.json.JSONObject;

public interface ADSDataSourceBindingBase {
  /**
   *
   * @return   Pagination format supported by this data source(ContinuationToken/SkipTop).
   */
  String getCallbackContextTemplate();

  /**
   *
   * @return  Subsequent calls needed?
   */
  String getCallbackRequiredTemplate();

  /**
   *
   * @return  Gets or sets the name of the data source.
   */
  String getDataSourceName();

  /**
   *
   * @return  Gets or sets the endpoint Id.
   */
  String getEndpointId();

  /**
   *
   * @return  Gets or sets the url of the service endpoint.
   */
  URL getEndpointUrl();

  /**
   *
   * @return      Gets or sets the authorization headers.
   */
  Map<String, String> getHeaders();

  /**
   *
   * @return  Defines the initial value of the query params
   */
  String getInitialContextTemplate();

  /**
   *
   * @return  Gets or sets the parameters for the data source.
   */
  JSONObject getParameters();

  /**
   *
   * @return  Gets or sets http request body
   */
  String getRequestContent();

  /**
   *
   * @return  Gets or sets http request verb
   */
  String getRequestVerb();

  /**
   *
   * @return  Gets or sets the result selector.
   */
  String getResultSelector();

  /**
   *
   * @return  Gets or sets the result template.
   */
  String getResultTemplate();

  /**
   *
   * @return  Gets or sets the target of the data source.
   */
  String getTarget();
}
