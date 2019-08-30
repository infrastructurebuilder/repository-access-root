package org.infrastructurebuilder.util.artifacts.azuredevops;

import java.util.Map;
import java.util.Optional;

/**
 * Acts in the same manner as Kohsuker Github (i.e. return null when not-found instead of Optional)
 * @author mykel.alvis
 *
 */
public interface ADSProject extends ADSNamedAndOwned<ADSOrg> {

  /**
   * @param artifactId
   * @return an ADSRepo if present or null if not found
   */
  Optional<ADSRepo> getRepository(String artifactId);

  Map<String, ADSRepo> getRepos();

}
