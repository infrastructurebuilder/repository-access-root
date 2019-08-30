package org.infrastructurebuilder.util.artifacts.azuredevops;

import java.net.URL;
import java.util.List;
import java.util.Optional;

public interface ADSRepo extends ADSNamedAndOwned<ADSProject> {

  ADSRelease getReleaseByTagName(String tag);

  URL getSSHUrl();

  boolean isFork();

  URL getRemoteUrl();

  Optional<ADSRepo> getParentRepository();

  List<URL> getValidRemoteUrls();

  URL getWebUrl();

  String getDefaultBranch();
}
