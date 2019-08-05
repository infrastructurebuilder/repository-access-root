package org.infrastructurebuilder.util.artifacts;

import java.util.Optional;

public interface GithubArtifactResolver {
  Optional<GAV> getFromGithubReleases(GAV source);
}
