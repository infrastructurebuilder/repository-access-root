package org.infrastructurebuilder.util.artifacts.azuredevops;

public interface ADSConfigurationVariableValue {

  /**
   *
   * @return  Gets and sets if a variable can be overridden at deployment time or not.
   */
  boolean isAllowOverride();

  /**
   *
   * @return  Gets or sets as variable is secret or not.
   */
  boolean isSecret();

  /**
   *
   * @return  Gets and sets value of the configuration variable.
   */
  String getValue();
}
