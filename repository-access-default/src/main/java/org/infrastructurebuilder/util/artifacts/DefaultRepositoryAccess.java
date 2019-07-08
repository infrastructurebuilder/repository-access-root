/**
 * Copyright © 2019 admin (admin@infrastructurebuilder.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.infrastructurebuilder.util.artifacts;

import static org.infrastructurebuilder.IBConstants.PASSWORD;
import static org.infrastructurebuilder.IBConstants.USERNAME;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.resolution.DependencyRequest;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transport.file.FileTransporterFactory;
import org.eclipse.aether.transport.http.HttpTransporterFactory;
import org.eclipse.aether.util.artifact.SubArtifact;
import org.eclipse.aether.util.graph.visitor.PreorderNodeListGenerator;
import org.eclipse.aether.util.repository.AuthenticationBuilder;
import org.eclipse.sisu.Typed;
import org.infrastructurebuilder.IBException;
import org.infrastructurebuilder.util.artifacts.impl.DefaultGAV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named("default")
@Typed(ArtifactServices.class)
public class DefaultRepositoryAccess implements ArtifactServices {
  public final static Function<Artifact, GAV> artifactToGAV = (art) -> {
    final Path p = Optional.ofNullable(art.getFile()).map(p2 -> p2.toPath()).orElse(null);
    return new DefaultGAV(art.getGroupId(), art.getArtifactId(), art.getClassifier(), art.getVersion(),
        art.getExtension()).withFile(p);
  };

  public static Artifact asArtifact(final GAV art) {
    return new DefaultArtifact(art.getDefaultSignaturePath());
  }

  public static Dependency asDependency(final GAV art, final String scope) {
    return new Dependency(asArtifact(art), scope);
  }

  public static GAV fromArtifact(final Artifact a) {
    return new DefaultGAV(a.getGroupId(), a.getArtifactId(), a.getClassifier(), a.getVersion(), a.getExtension())
        .withFile(Optional.ofNullable(a.getFile()).map(File::toPath).orElse(null));
  }

  public final static Logger log = LoggerFactory.getLogger(DefaultRepositoryAccess.class);
  public final static DefaultServiceLocator.ErrorHandler errorHandler = new DefaultServiceLocator.ErrorHandler() {
    @Override
    public void serviceCreationFailed(final Class<?> type, final Class<?> impl, final Throwable exception) {
      logger.error("ServiceCreateFail : type " + type.getCanonicalName() + " : impl " + impl.getCanonicalName(),
          exception);
    }
  };

  private static final String DEFAULT = "default";
  private final static Logger logger = LoggerFactory.getLogger(DefaultRepositoryAccess.class);

  private static RepositorySystem newRepositorySystem() {
    final DefaultServiceLocator locator = MavenRepositorySystemUtils.newServiceLocator();
    locator.addService(RepositoryConnectorFactory.class, BasicRepositoryConnectorFactory.class);
    locator.addService(TransporterFactory.class, FileTransporterFactory.class);
    locator.addService(TransporterFactory.class, HttpTransporterFactory.class);
    locator.setErrorHandler(errorHandler);

    return locator.getService(RepositorySystem.class);
  }

  private boolean _normalizeSnapshots = false;

  private String _password = null;
  private String _remote = null;
  private String _user = null;

  private IBListener listener;
  private final Path localRepoFile;
  private RepositorySystem repositorySystem;

  public DefaultRepositoryAccess(final String localRepository, final String validatingUsername,
      final String validatingPassword, final String remoteRepositoryUrl, final boolean n) {
    localRepoFile = Paths.get(Objects.requireNonNull(localRepository, "local repo")).toAbsolutePath();
    Optional<String> user = Optional.ofNullable(validatingUsername);
    Optional<String> password = Optional.ofNullable(validatingPassword);
    Optional<String> remote = Optional.ofNullable(remoteRepositoryUrl);
    _user = Objects.requireNonNull(user).orElse(null);
    _password = Objects.requireNonNull(password).orElse(null);
    _remote = Objects.requireNonNull(remote).orElse(null);
    _normalizeSnapshots = n;
    final HashMap<String, String> m = new HashMap<>();
    remote.map(u -> m.put(ArtifactServices.REMOTE_REPO_URL, u));
    m.put("maven.repo.local", localRepository);
    user.map(u -> m.put(USERNAME, u));
    password.map(u -> m.put(PASSWORD, u));

    final Properties p = new Properties();
    p.putAll(m);
    configureRepositorySystem(true);
  }

  @Override
  public GAV getArtifact(final GAV coords, final String scope) {
    final RepositorySystemSession session = newSession();
    return IBException.cet.withReturningTranslation(() -> {
      final ArtifactRequest dependencyRequest = new ArtifactRequest();

      dependencyRequest.setArtifact(asArtifact(coords));
      getRemoteRepositoryFromConfig().ifPresent(central -> dependencyRequest.addRepository(central));

      final ArtifactResult result = getRepositorySystem().resolveArtifact(session, dependencyRequest);
      final Artifact art = result.getArtifact();
      return artifactToGAV.apply(art);
    });
  }

  @Override
  public List<GAV> getArtifacts(final GAV coords, final String scope, final boolean includeUnresolved) {
    return getArtifactList(coords, scope, includeUnresolved).stream().map(a -> artifactToGAV.apply(a))
        .collect(Collectors.toList());
  }

  private List<Artifact> getArtifactList(final GAV coords, final String scope, final boolean includeUnresolved) {
    final PreorderNodeListGenerator nlg = getNlg(coords, scope);
    return nlg.getArtifacts(includeUnresolved);
  }

  @Override
  public String getClasspathOf(final GAV coords, final String scope, final boolean includeUnresolved) {
    final PreorderNodeListGenerator nlg = getNlg(coords, scope);
    return nlg.getClassPath();
  }

  @Override
  public String getClasspathOf(final GAV coords, final String scope, final List<GAV> additional,
      final boolean eliminateEquivalentFiles) {
    final String cpRoot = getNlg(coords, scope).getClassPath();
    final StringJoiner sj = new StringJoiner(File.pathSeparator);
    final List<String> splits = new ArrayList<>();
    splits.addAll(Arrays.asList(cpRoot.split(File.pathSeparator)));
    splits.forEach(i -> sj.add(i));
    for (final GAV gav : Objects.requireNonNull(additional)) {
      for (final String item : Arrays.asList(getNlg(gav, scope).getClassPath().split(File.pathSeparator)))
        if (!eliminateEquivalentFiles || !splits.contains(item)) {
          splits.add(item);
          sj.add(item);
        }
    }
    return sj.toString();
  }

  @Override
  public List<Path> getDependenciesOfClassifiedTypeFor(final GAV coords, final String scope, final String classifier,
      final String type, final boolean throwOnFail) {
    final List<Artifact> x = getArtifactList(coords, scope, false).stream()
        .map(a -> new SubArtifact(a, classifier, type)).collect(Collectors.toList());
    final Set<Path> resolvedSources = new HashSet<>(x.size());

    for (final Artifact artifact : x) {
      try {
        resolvedSources.addAll(getDependencies(fromArtifact(artifact), scope));
      } catch (final IBException e) {
        if (throwOnFail)
          throw e;

      }
    }
    return resolvedSources.stream().collect(Collectors.toList());
  }

  @Override
  /**
   * Always returns the local repo.  DefaultSettingsSupplier creates local repo if not previously present
   */
  public Path getLocalRepo() {
    return localRepoFile;
  }

  public Optional<String> getPassword() {
    return Optional.ofNullable(_password);
  }

  public Optional<String> getRemote() {
    return Optional.ofNullable(_remote);
  }

  @Override
  public Optional<URL> getRemoteRepo() {
    return getRemote().map(u -> IBException.cet.withReturningTranslation(() -> new URL(u)));
  }

  public RepositorySystem getRepositorySystem() {
    return repositorySystem;
  }

  public Optional<String> getUser() {
    return Optional.ofNullable(_user);
  }

  public boolean isNormalizeSnapshots() {
    return _normalizeSnapshots;
  }

  public RepositorySystemSession newSession() {
    final DefaultRepositorySystemSession session = MavenRepositorySystemUtils.newSession();
    if (listener == null) {
      listener = new IBListener();
    }
    session.setTransferListener(listener);
    session.setRepositoryListener(listener);

    final LocalRepository localRepo = new LocalRepository(getLocalRepo().toString());
    session.setLocalRepositoryManager(repositorySystem.newLocalRepositoryManager(session, localRepo));
    session.setTransferListener(listener);
    session.setRepositoryListener(listener);
    session.setConfigProperty("aether.artifactResolver.snapshotNormalization",
        isNormalizeSnapshots() ? "true" : "false");
    session.setOffline(!getRemote().isPresent());

    session.setReadOnly();

    return session;
  }

  private CollectRequest getCollectRequest(final GAV coords, final String scope) {
    final CollectRequest collectRequest = new CollectRequest();
    getRemoteRepositoryFromConfig().ifPresent(central -> collectRequest.addRepository(central));

    collectRequest.setRoot(asDependency(coords, scope));
    return collectRequest;
  }

  private PreorderNodeListGenerator getNlg(final GAV coords, final String scope) {
    final RepositorySystemSession session = newSession();
    return IBException.cet.withReturningTranslation(() -> {
      final CollectRequest collectRequest = getCollectRequest(coords, scope);
      final DependencyNode node = repositorySystem.collectDependencies(session, collectRequest).getRoot();
      final DependencyRequest dependencyRequest = new DependencyRequest();
      dependencyRequest.setRoot(node);

      repositorySystem.resolveDependencies(session, dependencyRequest);

      final PreorderNodeListGenerator nlg = new PreorderNodeListGenerator();
      node.accept(nlg);
      return nlg;
    });
  }

  private Optional<RemoteRepository> getRemoteRepositoryFromConfig() {
    RemoteRepository remoteRef = null;
    if (getUser().isPresent()) {
      remoteRef = new RemoteRepository.Builder(ArtifactServices.CENTRAL_REPO_ID, DEFAULT, getRemote().orElse(null))
          .setAuthentication(new AuthenticationBuilder().addUsername(getUser().get())
              .addPassword(getPassword()
                  .orElseThrow(() -> new IBException("No password supplied with user.  This is not allowed")))
              .build())
          .build();
    } else {
      final Optional<String> x = getRemote();
      if (x.isPresent()) {
        remoteRef = new RemoteRepository.Builder(ArtifactServices.CENTRAL_REPO_ID, DEFAULT,
            getRemote().orElseThrow(() -> new IBException("No remote repo URL supplied"))).build();
      }
    }
    return Optional.ofNullable(remoteRef);
  }

  protected void configureRepositorySystem(final boolean useGuice) {
    repositorySystem = newRepositorySystem();

  }

}
