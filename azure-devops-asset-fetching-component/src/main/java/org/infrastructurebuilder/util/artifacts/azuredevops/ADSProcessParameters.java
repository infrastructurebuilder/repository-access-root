package org.infrastructurebuilder.util.artifacts.azuredevops;

import java.util.List;

public interface ADSProcessParameters {

  /**
   *
   * @return  Represents binding of data source for the service endpoint request.
   */
  List<ADSDataSourceBindingBase> getDataSourceBindings();

  /**
   *
   * @return
   */
  List<ADSTaskInputDefinitionBase> getInputs();

  /**
   *
   * @return
   */
  List<ADSTaskSourceDefinitionBase> getSourceDefinitions();

}
