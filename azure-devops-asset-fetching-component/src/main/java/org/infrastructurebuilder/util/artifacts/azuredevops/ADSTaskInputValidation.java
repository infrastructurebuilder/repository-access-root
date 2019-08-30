package org.infrastructurebuilder.util.artifacts.azuredevops;

public interface ADSTaskInputValidation {
  /**
   * @return  Conditional expression
   */
  String getExpression();

  /**
   * @return  Message explaining how user can correct if validation fails}
   */
  String getMessage();
}
