package org.infrastructurebuilder.util.artifacts.azuredevops;

import org.json.JSONObject;

public interface ADSReleaseIssue {

  /**
   *
   * @return Issue data.
   */
  JSONObject getData();

  /**
   * @return   Issue type, for example error, warning or info.
   */
  String getIssueType();

  /**
   * @return  Issue message.
   */
  String getMessage();

}
