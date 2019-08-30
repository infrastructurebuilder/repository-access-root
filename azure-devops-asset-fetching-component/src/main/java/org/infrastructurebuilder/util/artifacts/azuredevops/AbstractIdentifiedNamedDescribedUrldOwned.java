package org.infrastructurebuilder.util.artifacts.azuredevops;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.util.Optional;

public abstract class AbstractIdentifiedNamedDescribedUrldOwned<O> {

  private final O owner;
  private final String name;
  private final ADSClient client;
  private final Optional<String> description;
  private final URL url;
  private final String id;

  public AbstractIdentifiedNamedDescribedUrldOwned(O owner, String id, String name, URL url, Optional<String> description,
      ADSClient client) {
    this.id = requireNonNull(id);
    this.owner = requireNonNull(owner);
    this.name = requireNonNull(name);
    this.client = requireNonNull(client);
    this.description = requireNonNull(description);
    this.url = requireNonNull(url);
  }

  public final String getName() {
    return this.name;
  }

  public final O getOwner() {
    return this.owner;
  }

  public ADSClient getClient() {
    return client;
  }

  public Optional<String> getDescription() {
    return description;
  }

  public URL getUrl() {
    return url;
  }

  public String getId() {
    return id;
  }
}
