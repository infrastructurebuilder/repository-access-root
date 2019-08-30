package org.infrastructurebuilder.util.artifacts.azuredevops;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.infrastructurebuilder.IBConstants.AZUREDEVOPS;
import static org.infrastructurebuilder.IBException.cet;

import java.io.File;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.activation.MimeType;
import javax.inject.Named;

import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.ssl.SSLContexts;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.wagon.proxy.ProxyInfo;
import org.infrastructurebuilder.IBException;
import org.infrastructurebuilder.util.ProxyProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.download.maven.plugin.internal.HttpFileRequester;
import com.googlecode.download.maven.plugin.internal.LoggingProgressReport;
import com.googlecode.download.maven.plugin.internal.SilentProgressReport;

@Named(AZUREDEVOPS)
public class DefaultADSClient implements ADSClient {

  private final static PoolingHttpClientConnectionManager CONN_POOL;

  static {
    CONN_POOL = new PoolingHttpClientConnectionManager(
        RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", PlainConnectionSocketFactory.getSocketFactory())
            .register("https",
                new SSLConnectionSocketFactory(SSLContexts.createSystemDefault(),
                    new String[] { "SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2" }, null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier()))
            .build(),
        null, null, null, 1, TimeUnit.MINUTES);
  }

  private static final String ADS_DEFAULT_ENDPOINT = "https://dev.azure.com/";
  private static final String ADS_DEFAULT_API_VERSION = "4.1";
  private final Map<String, ADSOrg> orgMap = new HashMap<>();
  private final String endpoint;
  private final String apiVersion;
  private final int readTimeOut = 1;
  private final Optional<String> username;
  private final Optional<String> oauth;
  private final Optional<ProxyProxy> proxy;
  public final static Logger log = LoggerFactory.getLogger(DefaultADSClient.class);

  public DefaultADSClient(Optional<String> tfsServer, Optional<String> apiVersion, Optional<String> tfsUsername,
      Optional<String> tfsOauth, Optional<ProxyProxy> proxyConfig) {
    this(requireNonNull(tfsServer).orElse(ADS_DEFAULT_ENDPOINT),
        requireNonNull(apiVersion).orElse(ADS_DEFAULT_API_VERSION), requireNonNull(tfsUsername),
        requireNonNull(tfsOauth), requireNonNull(proxyConfig));
  }

  private DefaultADSClient(String endpoint, String apiVersion, Optional<String> username, Optional<String> oauth,
      Optional<ProxyProxy> proxy) {
    this.endpoint = endpoint;
    this.apiVersion = apiVersion;
    this.username = username;
    this.oauth = oauth.map(o -> Base64.getEncoder().encodeToString(o.getBytes(StandardCharsets.UTF_8)));
    this.proxy = proxy;

    // https://app.vssps.visualstudio.com/_apis/accounts

    // TODO Try to get org lists using this technique -> https://stackoverflow.com/questions/54762368/get-all-organizations-in-azure-devops-using-rest-api

  }

  @Override
  public Optional<ADSOrg> getOrganization(String orgId) {
    try {
      return of(new DefaultADSOrg(UUID.randomUUID().toString(), // FIXME Random ID is wrong
          orgId, // Org Id is right
          IBException.cet.withReturningTranslation(() -> new URL(this.endpoint)), // URL is wrong
          Optional.empty(), // No description as of now
          this));
    } catch (Exception e) {
      // TODO Log e?
    }
    return empty();
  }

  private final String _getParams(Map<String, String> t) {
    Map<String, String> params = new HashMap<>();
    params.putAll(requireNonNull(t));
    params.put("api-version", this.apiVersion);
    final StringJoiner sb = new StringJoiner("&");
    params.entrySet().forEach(e -> {
      sb.add(ADSClient.encode(e.getKey()) + "=" + ADSClient.encode(e.getValue()));
    });
    return sb.toString();

  }

  @Override
  public HttpUriRequest getGet(String fragment, Optional<Map<String, String>> params) {
    URI u = cet.withReturningTranslation(() -> new URI(
        this.endpoint + "/" + fragment + "&" + _getParams(requireNonNull(params).orElse(Collections.emptyMap()))));
    HttpGet get = new HttpGet(u);
    this.oauth.ifPresent(o -> get.addHeader("Authorization", o));
    get.addHeader("Accept", "application/json");
    return get;
  }

  @Override
  public HttpClient getHttpClient() {
    return null;
  }

  private void doDownloadViaGet(final URI uri, final File outputFile) throws Exception {
    final RequestConfig requestConfig;
    if (readTimeOut > 0) {
      getLog().info("Read Timeout is set to " + readTimeOut + " milliseconds (apprx "
          + Math.round(readTimeOut * 1.66667e-5) + " minutes)");
      requestConfig = RequestConfig.custom().setConnectTimeout(readTimeOut).setSocketTimeout(readTimeOut).build();
    } else {
      requestConfig = RequestConfig.DEFAULT;
    }

    CredentialsProvider credentialsProvider = null;
    if (username.isPresent()) {
      getLog().debug("providing custom authentication");
      getLog().debug("username: " + username + " and password: ***");

      credentialsProvider = new BasicCredentialsProvider();
      credentialsProvider.setCredentials(new AuthScope(uri.getHost(), uri.getPort()),
          new UsernamePasswordCredentials(username.get(), oauth.orElse(null)));

    }

    final HttpRoutePlanner routePlanner;
    ProxyProxy proxyInfo = this.proxy.orElse(null);
    if (proxyInfo != null && proxyInfo.getHost() != null && ProxyInfo.PROXY_HTTP.equals(proxyInfo.getProtocol())) {
      routePlanner = new DefaultProxyRoutePlanner(new HttpHost(proxyInfo.getHost(), proxyInfo.getPort()));
      if (proxyInfo.getUsername().isPresent()) {
        final Credentials creds;
        // TODO Figure out what's up with NTLM creds in the proxies
        //        if (proxyInfo.getNtlmHost() != null || proxyInfo.getNtlmDomain() != null) {
        //          creds = new NTCredentials(proxyInfo.getUsername(), proxyInfo.getPassword(), proxyInfo.getNtlmHost(),
        //              proxyInfo.getNtlmDomain());
        //        } else {
        creds = new UsernamePasswordCredentials(proxyInfo.getUsername().get(), proxyInfo.getPassword().orElse(null));
        //        }
        AuthScope authScope = new AuthScope(proxyInfo.getHost(), proxyInfo.getPort());
        if (credentialsProvider == null) {
          credentialsProvider = new BasicCredentialsProvider();
        }
        credentialsProvider.setCredentials(authScope, creds);
      }
    } else {
      routePlanner = new SystemDefaultRoutePlanner(ProxySelector.getDefault());
    }

    final CloseableHttpClient httpClient = HttpClientBuilder.create().setConnectionManager(CONN_POOL)
        .setConnectionManagerShared(true).setRoutePlanner(routePlanner).build();

    final HttpFileRequester fileRequester = new HttpFileRequester(httpClient, new SilentProgressReport(null));

    final HttpClientContext clientContext = HttpClientContext.create();
    clientContext.setRequestConfig(requestConfig);
    if (credentialsProvider != null) {
      clientContext.setCredentialsProvider(credentialsProvider);
    }

    fileRequester.download(uri, outputFile, clientContext);
  }

  private Log getLog() {
    // TODO Auto-generated method stub
    return null;
  }

}
