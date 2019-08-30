package org.infrastructurebuilder.util.artifacts.azuredevops;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.xml.ws.RespectBinding;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.infrastructurebuilder.IBException;
import org.infrastructurebuilder.util.IBUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class DefaultADSOrg extends AbstractIdentifiedNamedDescribedUrldOwned<ADSClient> implements ADSOrg {
  final static Optional<ADSOrg> from(Optional<ADSOrg> o) {
    return requireNonNull(o).map(DefaultADSOrg::new);
  }

  private Map<String, ADSProject> projects;
  private Map<String, ADSProcess> processes;
  private Map<String, ADSTeam> teams;

  private DefaultADSOrg(ADSOrg orig) {
    super(requireNonNull(orig).getOwner(), requireNonNull(orig).getId(), requireNonNull(orig).getName(),
        requireNonNull(orig).getUrl(), requireNonNull(orig).getDescription(), requireNonNull(orig).getOwner());
  }

  public DefaultADSOrg(String id, String name, URL url, Optional<String> desc, ADSClient owner) {
    super(requireNonNull(owner), id, name, url, desc, requireNonNull(owner));
    this.projects = getProjects().stream().collect(toMap(k -> k.getName(), Function.identity()));
    this.processes = getProcesses().stream().collect(toMap(k -> k.getName(), Function.identity()));
    this.teams = getTeams().stream().collect(toMap(k -> k.getName(), Function.identity()));
  }

  private List<? extends ADSProject> getProjects() {
    ADSClient c = getClient();
    try {
      // GET https://dev.azure.com/{organization}/_apis/projects/{projectId}?api-version=4.1
      Optional<JSONObject> j = orgCall("projects", Optional.empty());
      return j.map(resp -> IBUtils.asJSONObjectStream(resp.getJSONArray("values"))
          .map(j2 -> new DefaultADSProject(this, j2)).collect(Collectors.toList())).orElse(Collections.emptyList());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return emptyList();
  }

  public Optional<JSONObject> orgCall(String command, Optional<Map<String, String>> params) throws IOException {
    return _call(new StringJoiner("/").add(this.getName()).add("_apis"), command, params);
  }

  @Override
  public Optional<JSONObject> call(String project, String command, Optional<Map<String, String>> params)
      throws IOException {
    return _call(new StringJoiner("/").add(this.getName()).add(requireNonNull(project)).add("_apis"), command, params);
  }

  public Optional<JSONObject> _call(StringJoiner preCommand, String command, Optional<Map<String, String>> params)
      throws IOException {
    String fragment = ADSClient.encode(requireNonNull(preCommand).add(command).toString());
    HttpUriRequest r = getClient().getGet(fragment.toString(), params);
    HttpResponse resp = getClient().getHttpClient().execute(r);
    switch (resp.getStatusLine().getStatusCode()) {
    case 200:
      try (InputStream ins = resp.getEntity().getContent();
          InputStreamReader ir = new InputStreamReader(ins);
          BufferedReader br = new BufferedReader(ir)) {
        StringBuffer str = new StringBuffer();
        for (String line = ""; (line = br.readLine()) != null;) {
          str.append(line);
        }
        return of(new JSONObject(str));
      }
    default:
      return Optional.empty();
    }
  }

  private List<ADSProcess> getProcesses() {
    // TODO Auto-generated method stub
    return null;
  }

  private List<ADSTeam> getTeams() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Optional<ADSProject> getProject(String id) {
    if (this.projects == null) {
      this.projects = IBException.cet.withReturningTranslation(() -> getProjects()).stream()
          .collect(Collectors.toMap(k -> k.getId(), Function.identity()));
    }
    return Optional.ofNullable(this.projects.get(requireNonNull(id)));
  }

}
