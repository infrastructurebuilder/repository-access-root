package org.infrastructurebuilder.util.artifacts.azuredevops;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;

public interface ADSOrg extends ADSNamedAndOwned<ADSClient> {
  Optional<ADSProject> getProject(String id);

  Optional<JSONObject> call(String project, String command, Optional<Map<String, String>> params) throws IOException;
}
