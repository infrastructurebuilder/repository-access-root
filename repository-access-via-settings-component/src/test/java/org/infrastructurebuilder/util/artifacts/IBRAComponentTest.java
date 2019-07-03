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
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.settings.Mirror;
import org.apache.maven.settings.Settings;
import org.codehaus.plexus.ContainerConfiguration;
import org.codehaus.plexus.DefaultContainerConfiguration;
import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.PlexusConstants;
import org.codehaus.plexus.classworlds.ClassWorld;
import org.eclipse.aether.util.artifact.JavaScopes;
import org.eclipse.sisu.space.SpaceModule;
import org.eclipse.sisu.space.URLClassSpace;
import org.eclipse.sisu.wire.WireModule;
import org.infrastructurebuilder.IBException;
import org.infrastructurebuilder.util.Layout;
import org.infrastructurebuilder.util.MirrorProxy;
import org.infrastructurebuilder.util.SettingsProxy;
import org.infrastructurebuilder.util.SettingsSupplier;
import org.infrastructurebuilder.util.artifacts.impl.DefaultGAV;
import org.infrastructurebuilder.util.config.WorkingPathSupplier;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class IBRAComponentTest {

  private static Path target;
  private final static WorkingPathSupplier wps = new WorkingPathSupplier();

  @BeforeClass
  public static void beforeClass() throws Exception {
    target = wps.getRoot();

  }

  private DefaultPlexusContainer container;
  private String pLocal;
  private Path root;
  private ArtifactServices utils;

  private static ClassWorld kw;

  private static ContainerConfiguration dpcreq;
  private IBRAComponentFromSettings uFact;
  private ArtifactServices utilsLocal;
  private IBRAComponentFromSettings offline;
  private ArtifactServices utils2;

  @Before
  public void setUp() throws Exception {

    final String mavenCoreRealmId = "testing";
    kw = new ClassWorld(mavenCoreRealmId, getClass().getClassLoader());

    dpcreq = new DefaultContainerConfiguration().setClassWorld(kw).setClassPathScanning(PlexusConstants.SCANNING_INDEX)
        .setName(mavenCoreRealmId);
    container = new DefaultPlexusContainer(dpcreq,
        new WireModule(new SpaceModule(new URLClassSpace(kw.getClassRealm(mavenCoreRealmId)))));
    root = target.resolve(UUID.randomUUID().toString());
    Files.createDirectories(root);
    pLocal = root.toString();
    SettingsSupplier ss = container.lookup(SettingsSupplier.class);
    uFact = new IBRAComponentFromSettings(ss);
    utils = uFact.create(Optional.empty(), Optional.empty(), true);
    utils2 = uFact.create(Optional.of("probably@#$@#$@#$notpossible"), Optional.of("probablyimPOSSI#@$@#$@#$IBVLE"),
        false);
    utilsLocal = uFact.createLocal(true);

    SettingsSupplier s2 = new SettingsSupplier() {
      @Override
      public SettingsProxy get() {
        MirrorProxy mirror = new MirrorProxy("mirrorId", Layout.DEFAULT, Arrays.asList("*"), Collections.emptyList(),
            Optional.of("mirror"), IBException.cet.withReturningTranslation(() -> new URL("http://www.example.org")));
        SettingsProxy sss = new SettingsProxy(true, wps.get(), Charset.defaultCharset(), Collections.emptyList(),
            Collections.emptyList(), Arrays.asList(mirror), Collections.emptyList(), Collections.emptyList());
        return sss;
      }
    };

    offline = new IBRAComponentFromSettings(s2);

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
  public void testOffline() {
    ArtifactServices o = offline.create(Optional.empty(), Optional.of("mirrorId"), true);// Still gonna be local because offline
    assertFalse(o.getRemoteRepo().isPresent());
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
  public void testImpossibleMirror() {

    ArtifactServices l = uFact.create(Optional.empty(), Optional.of("MAKESASDADQ#@E@#$#@$@#$"), true);
    assertEquals(ArtifactServices.CENTRAL_REPO_URL.toExternalForm(), l.getRemoteRepo().get().toExternalForm());

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
