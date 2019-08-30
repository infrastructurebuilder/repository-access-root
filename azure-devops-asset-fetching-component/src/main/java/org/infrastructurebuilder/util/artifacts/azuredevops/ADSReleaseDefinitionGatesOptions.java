package org.infrastructurebuilder.util.artifacts.azuredevops;

public interface ADSReleaseDefinitionGatesOptions {
  /**
   *
   * @return  Gets or sets as the gates enabled or not.
   */
  boolean isEnabled();

  /**
   *
   * @returnGets or sets the minimum duration for steady results after a successful gates evaluation.
   */
  int getMinimumSuccessDuration();

  /**
   *
   * @returnGets or sets the time between re-evaluation of gates.
   */
  int getSamplingInterval();

  /**
   *
   * @returnGets or sets the delay before evaluation.
   */
  int getStabilizationTime();

  /**
   *
   * @return Gets or sets the timeout after which gates fail.
   */
  int getTimeout();
}
