package org.infrastructurebuilder.util.artifacts.azuredevops;

public interface ADSEnvironmentOptions {
  /**
   *
   * @return  Gets and sets as the auto link workitems or not.
   */
  boolean isAutoLinkWorkItems();

  /**
   *
   * @return  Gets and sets as the badge enabled or not.
   */
  boolean isBadgeEnabled();

  /**
   *
   * @return  Gets and sets as the publish deployment status or not.
   */
  boolean isPublishDeploymentStatus();

  /**
   * @return   Gets and sets as the.pull request deployment enabled or not.
   */
  boolean isPullRequestDeploymentEnabled();
}
