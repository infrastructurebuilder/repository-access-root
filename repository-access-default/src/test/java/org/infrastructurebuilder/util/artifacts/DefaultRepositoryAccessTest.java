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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.settings.Mirror;
import org.apache.maven.settings.Server;
import org.apache.maven.settings.Settings;
import org.codehaus.plexus.ContainerConfiguration;
import org.codehaus.plexus.DefaultContainerConfiguration;
import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.PlexusConstants;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.classworlds.ClassWorld;
import org.eclipse.aether.util.artifact.JavaScopes;
import org.eclipse.sisu.space.SpaceModule;
import org.eclipse.sisu.space.URLClassSpace;
import org.eclipse.sisu.wire.WireModule;
import org.infrastructurebuilder.IBException;
import org.infrastructurebuilder.util.IBUtils;
import org.infrastructurebuilder.util.Layout;
import org.infrastructurebuilder.util.MirrorProxy;
import org.infrastructurebuilder.util.ServerProxy;
import org.infrastructurebuilder.util.SettingsProxy;
import org.infrastructurebuilder.util.SettingsSupplier;
import org.infrastructurebuilder.util.artifacts.impl.DefaultGAV;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DefaultRepositoryAccessTest {

  private static final String CENTRAL = "https://repo.maven.apache.org/maven2/";

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  private String localRepoString = System.getProperty("user.home") + File.separator + ".m2" + File.separator
      + "repository";

  private String pLocal;

  private Path target;

  ArtifactServices utils, utilsLocal;

  private SettingsSupplier ss;

  private PlexusContainer c;

  private SettingsProxy settings;
  private org.codehaus.plexus.classworlds.ClassWorld kw;
  private ContainerConfiguration dpcreq;

  @Before
  public void setUp() throws Exception {
    MirrorProxy falseMirror = new MirrorProxy("default", Layout.DEFAULT, Arrays.asList("*"), Collections.emptyList(),
        "name", ArtifactServices.CENTRAL_REPO_URL);
    List<MirrorProxy> fList = Arrays.asList(falseMirror);

    final String mavenCoreRealmId = "testing";
    kw = new ClassWorld(mavenCoreRealmId, getClass().getClassLoader());

    dpcreq = new DefaultContainerConfiguration().setClassWorld(kw).setClassPathScanning(PlexusConstants.SCANNING_INDEX)
        .setName(mavenCoreRealmId);
    c = new DefaultPlexusContainer(dpcreq,
        new WireModule(new SpaceModule(new URLClassSpace(kw.getClassRealm(mavenCoreRealmId)))));
    ss = c.lookup(SettingsSupplier.class);
    settings = ss.get();
    pLocal = settings.getLocalRepository().toAbsolutePath().toString();
    //    pLocal = Paths.get(System.getProperty("user.home"), ".m2", "repository").toAbsolutePath().toString();
    target = Paths.get(Optional.ofNullable(System.getProperty("target")).orElse("./target")).toRealPath()
        .toAbsolutePath();
    final Path pp = target.resolve("testrepo").toAbsolutePath();
    ServerProxy testServer = settings.getServer("test")
        .orElseThrow(() -> new IBException("The server 'test' must be in settings"));
    List<MirrorProxy> m = Optional.ofNullable(settings.getMirrors()).orElse(fList);
    if (m.size() == 0)
      m = fList;
    MirrorProxy mirrors = m.get(0); // FIXME maybe we need a test mirror?
    IBUtils.deletePath(pp);
    assertFalse(pp.toString() + " does not exist ", Files.isDirectory(pp));
    Files.createDirectories(pp);
    assertTrue(pp.toString() + " does exist ", Files.isDirectory(pp));
    pLocal = pp.toString();
    localRepoString = pLocal;

    utils = new DefaultRepositoryAccess(pLocal, testServer.getPrincipal().orElse(null),
        testServer.getSecret().orElse(null), mirrors.getUrl().toExternalForm(), true);

    utilsLocal = new DefaultRepositoryAccess(localRepoString, null, null, null, true);

  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testCons() {
    DefaultRepositoryAccess.errorHandler.serviceCreationFailed(ArtifactServices.class, DefaultRepositoryAccess.class,
        new RuntimeException("I'm a runtime exception"));
  }

  @Test
  public void testGetArtifact() {
    final GAV c = new DefaultGAV("junit", "junit", null, "4.12", "jar");
    final GAV l = utils.getArtifact(c, JavaScopes.COMPILE);
    assertNotNull(l);
    assertTrue("File exists", l.getFile().get().toFile().exists());
  }

  @Test
  public void testGetDependencies() {
    final GAV c = new DefaultGAV("junit", "junit", null, "3.8.2", "pom");
    List<Path> l;
    l = utils.getDependencies(c, JavaScopes.COMPILE);
    assertNotNull(l);
    assertEquals("Length == 1", 1, l.size());
  }

  @Test
  public void testGetDependenciesClasspath() {
    final GAV c = new DefaultGAV("junit", "junit", null, "4.12", "jar");
    final String l = utils.getClasspathOf(c, JavaScopes.RUNTIME, true);
    final String[] split = l.split(File.pathSeparator);
    final StringJoiner sb = new StringJoiner(File.pathSeparator);
    for (String s : split) {
      if (s.startsWith(pLocal)) {
        s = "LOCALREPO" + s.substring(pLocal.length());
      }
      sb.add(s);
    }

    assertNotNull(l);

  }

  @Test
  public void testGetDependenciesClasspathAddl() {
    final GAV c = new DefaultGAV("junit", "junit", null, "4.12", "jar");
    final GAV c2 = new DefaultGAV("junit", "junit", null, "3.7", "jar");
    final String l = utils.getClasspathOf(c, JavaScopes.RUNTIME, Arrays.asList(c2, c), true);
    final String l1 = utils.getClasspathOf(c, JavaScopes.RUNTIME, Arrays.asList(c2, c), false);

    assertTrue(l.toLowerCase().contains("junit-3.7"));
    assertTrue(l.toLowerCase().contains("junit-4.12"));
    final String[] splits = l.split(File.pathSeparator);
    assertEquals("3items in l", 3, splits.length);
    final String[] splits1 = l1.split(File.pathSeparator);
    assertEquals("Five items in l1", 5, splits1.length);
    final String[] split = l.split(File.pathSeparator);
    final StringJoiner sb = new StringJoiner(File.pathSeparator);
    for (String s : split) {
      if (s.startsWith(pLocal)) {
        s = "LOCALREPO" + s.substring(pLocal.length());
      }
      sb.add(s);
    }

    assertNotNull(l);

  }

  @Test
  public void testGetLocalArtifact() {
    final GAV c = new DefaultGAV("junit", "junit", null, "4.12", "jar");
    GAV l = utils.getArtifact(c, JavaScopes.COMPILE);
    l = utilsLocal.getArtifact(c, JavaScopes.COMPILE);
    assertNotNull(l);
    assertTrue("File exists", l.getFile().get().toFile().exists());
  }

  @Test
  public void testGetLocalArtifacts() {
    final GAV c = new DefaultGAV("junit", "junit", null, "4.12", "jar");
    List<GAV> l = utils.getArtifacts(c, JavaScopes.COMPILE, false);
    assertNotNull(l);
    assertEquals("Files exists", 2, l.size());

    l = utilsLocal.getArtifacts(c, JavaScopes.COMPILE, false);
    assertNotNull(l);
    assertEquals("Files exists", 2, l.size());

  }

  @Test
  public void testGetLocalRepo() {
    assertEquals("Local repo is /", localRepoString, utils.getLocalRepo().toString());
  }

  @Test(expected = IBException.class)
  public void testNotGetDependencies() {
    final GAV c = new DefaultGAV("junit222", "junit", null, "4.12", "jar");
    List<Path> l;
    l = utils.getDependencies(c, JavaScopes.COMPILE);
    assertNotNull(l);
    assertEquals("Length == 2", 2, l.size());
  }

  @Test
  public void testSourceDeps() {
    final GAV c = new DefaultGAV("junit", "junit", null, "4.12", "jar");
    List<Path> l;
    l = utils.getDependencySourceJars(c, JavaScopes.COMPILE);
    assertNotNull(l);
    assertEquals("Length == 3", 3, l.size());
  }

  @Test(expected = IBException.class)
  public void testSourceDepsFAIL() {
    final GAV c = new DefaultGAV("junit22", "junit", null, "4.12", "jar");
    List<Path> l;
    l = utils.getDependencySourceJars(c, JavaScopes.COMPILE);
    assertNotNull(l);
    assertEquals("Length == 3", 3, l.size());
  }

  @Test
  public void testSystemScopedDependency() {
    final Pattern p = Pattern.compile("([^: ]+):([^: ]+)(:([^: ]*)(:([^: ]+))?)?:([^: ]+)");
    final Matcher m = p.matcher("net.sf.corn:corn-cps:jar:1.1.7");
    if (!m.matches())
      throw new IllegalArgumentException(
          "Bad artifact coordinates , expected format is <groupId>:<artifactId>[:<extension>[:<classifier>]]:<version>");

    final GAV corn = new DefaultGAV("net.sf.corn", "corn-cps", (String) null, "1.1.7", "jar");
    final List<GAV> l = utils.getArtifactsRuntime(corn);
    assertNotNull("get Instance", l);
    assertEquals("list is 2 items", 2, l.size());
  }

}
