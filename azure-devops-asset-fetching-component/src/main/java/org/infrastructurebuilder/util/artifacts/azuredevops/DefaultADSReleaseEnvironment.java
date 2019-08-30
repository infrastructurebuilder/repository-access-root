package org.infrastructurebuilder.util.artifacts.azuredevops;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;

public class DefaultADSReleaseEnvironment extends AbstractIdentifiedNamedXX<ADSRelease>
    implements ADSReleaseEnvironment {

  public DefaultADSReleaseEnvironment(ADSRelease owner, JSONObject src) {
    super(owner, src.getInt("releaseId"), src.optString("name", "UNNAMED"),
        Optional.ofNullable(src.optString("description", null)));
  }
}
