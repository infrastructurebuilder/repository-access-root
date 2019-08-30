package org.infrastructurebuilder.util.artifacts.azuredevops;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import org.json.JSONObject;

public abstract class AbstractIdentifiedNamedXX<O> {

  private final O owner;
  private final String name;
  private final Optional<String> description;
  private final int id;

  public AbstractIdentifiedNamedXX(O owner, JSONObject src) {
    this(owner, src.getInt("id"), src.optString("name", "UNNAMED"),
        Optional.ofNullable(src.optString("description", null)));
  }

  AbstractIdentifiedNamedXX(O owner, int id, String name, Optional<String> description) {
    this.id = id;
    this.owner = requireNonNull(owner);
    this.name = requireNonNull(name);
    this.description = requireNonNull(description);
  }

  public final String getName() {
    return this.name;
  }

  public final O getOwner() {
    return this.owner;
  }

  public Optional<String> getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }
}
