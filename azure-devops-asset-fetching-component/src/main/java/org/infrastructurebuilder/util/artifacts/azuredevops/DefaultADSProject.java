package org.infrastructurebuilder.util.artifacts.azuredevops;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.infrastructurebuilder.IBException;
import org.infrastructurebuilder.util.IBUtils;
import org.json.JSONObject;

public class DefaultADSProject extends AbstractIdentifiedNamedDescribedUrldOwned<ADSOrg> implements ADSProject {

  private Map<String, ADSRepo> repos; // Lazy load the repos

  public DefaultADSProject(ADSOrg owner, JSONObject src) {
    super(owner, src.getString("id"), src.getString("name"),
        IBException.cet.withReturningTranslation(() -> new URL(src.getString("url"))),
        Optional.ofNullable(src.getString("description")), owner.getClient());
  }

  @Override
  public Map<String, ADSRepo> getRepos() {
    if (this.repos == null) {
      // Lazy load the repos
      try {
        Optional<JSONObject> j = getOwner().call(this.getId(),
            new StringJoiner("/").add("git").add("repositories").toString(), Optional.empty());
        j.ifPresent(resp -> {
          IBUtils.asJSONObjectStream(resp.getJSONArray("values"));
        });

      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return repos;
  }

  @Override
  public Optional<ADSRepo> getRepository(String artifactId) {
    return Optional.ofNullable(getRepos().get(artifactId));
  }

}
