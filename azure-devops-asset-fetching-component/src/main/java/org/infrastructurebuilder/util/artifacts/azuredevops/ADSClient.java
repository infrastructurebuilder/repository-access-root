package org.infrastructurebuilder.util.artifacts.azuredevops;

import static org.infrastructurebuilder.IBException.cet;

import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;

public interface ADSClient {
  public static String encode(String x) {
    return cet.withReturningTranslation(() -> URLEncoder.encode(x, "UTF-8"));
  }

  Optional<ADSOrg> getOrganization(String orgId);

  HttpUriRequest getGet(String fragment, Optional<Map<String, String>> params);

  default HttpUriRequest getGet(String fragment) {
    return getGet(fragment, Optional.empty());
  }

  HttpClient getHttpClient();

}
