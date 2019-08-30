package org.infrastructurebuilder.util.artifacts.azuredevops;

import java.net.URL;
import java.util.Optional;

/**
 * Most (but not all) assets in this component set have a name and an "owner", which
 * is the object reference that maps to where they came from
 *
 * @author mykel.alvis
 *
 * @param <O> The type of the "owner" object
 */
public interface ADSNamedAndOwned<O> extends ADSClientHolder {

  /**
   * @return a non-null instance of the owning object
   */
  O getOwner();

  /**
   * @return a non-null name for this item
   */
  String getName();

  String getId();

  Optional<String> getDescription();

  URL getUrl();

}
