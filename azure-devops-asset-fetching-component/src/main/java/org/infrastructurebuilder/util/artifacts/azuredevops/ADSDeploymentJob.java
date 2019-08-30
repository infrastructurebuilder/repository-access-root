package org.infrastructurebuilder.util.artifacts.azuredevops;

import java.util.List;

public interface ADSDeploymentJob {

  /**
   * @return  Parent task of all executed tasks.
   */
  ADSReleaseTask getJob();

  /**
   * @return  List of executed tasks with in job.
   */
  List<ADSReleaseTask> getTasks();
}
