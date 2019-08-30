package org.infrastructurebuilder.util.artifacts.azuredevops;

import java.util.Date;

public interface ADSIgnoredGate {
  /**
   * @return Gets the date on which gate is last ignored.
   */
  Date getLastModifiedOn();

  /**
   * @return Name of gate ignored
   */
  String getName();
}
