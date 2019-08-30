package org.infrastructurebuilder.util.artifacts.azuredevops;

import java.util.List;

import org.json.JSONObject;

public interface ADSTaskInputDefinitionBase {

  List<String> getAliases();

  String getDefaultValue();

  String getGroupName();

  String getHelpMarkDown();

  String getLabel();

  String getName();

  JSONObject getOptions();

  JSONObject getProperties();

  boolean isRequired();

  String getType();

  ADSTaskInputValidation getValidation();

  String getVisibleRule();
}
