package org.infrastructurebuilder;
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

//package org.infrastructurebuilder;
//
//import static org.junit.Assert.assertNotNull;
//
//import java.util.Map;
//import java.util.Optional;
//import java.util.concurrent.ConcurrentHashMap;
//
//import org.codehaus.plexus.ContainerConfiguration;
//import org.codehaus.plexus.DefaultContainerConfiguration;
//import org.codehaus.plexus.DefaultPlexusContainer;
//import org.codehaus.plexus.PlexusConstants;
//import org.codehaus.plexus.PlexusContainer;
//import org.codehaus.plexus.classworlds.ClassWorld;
//import org.eclipse.sisu.space.SpaceModule;
//import org.eclipse.sisu.space.URLClassSpace;
//import org.eclipse.sisu.wire.WireModule;
//import org.infrastructurebuilder.util.artifacts.ArtifactServices;
//import org.infrastructurebuilder.util.artifacts.IBRAComponentFromSettings;
//import org.infrastructurebuilder.util.artifacts.ArtifactServicesManager;
//import org.infrastructurebuilder.util.artifacts.GAV;
//import org.infrastructurebuilder.util.artifacts.impl.DefaultGAV;
//import org.infrastructurebuilder.utils.settings.SettingsSupplier;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//public class ArtifactServicesManagerTest {
//
//  @BeforeClass
//  public static void setUpBeforeClass() throws Exception {
//  }
//
//  private ArtifactServices a;
//  private ArtifactServicesManager am;
//  private DefaultContainerConfiguration cc;
//  private PlexusContainer container;
//  private ArtifactServices defaultAS, addlAS, third;
//
//  private GAV gavNoClassifier, gavClassifier, cfNoClassifier;
//  private ClassWorld kw;
//  private ContainerConfiguration dpcreq;
//
//  @Test
//  public void hasAS() {
//    assertNotNull(a);
//  }
//
//  @Before
//  public void setUp() throws Exception {
//
//    final String mavenCoreRealmId = "testing";
//    kw = new ClassWorld(mavenCoreRealmId, getClass().getClassLoader());
//
//    dpcreq = new DefaultContainerConfiguration().setClassWorld(kw).setClassPathScanning(PlexusConstants.SCANNING_INDEX)
//        .setName(mavenCoreRealmId);
//    container = new DefaultPlexusContainer(dpcreq,
//        new WireModule(new SpaceModule(new URLClassSpace(kw.getClassRealm(mavenCoreRealmId)))));
//
//    SettingsSupplier ss = container.lookup(SettingsSupplier.class);
//    IBRAComponentFromSettings uFact = new IBRAComponentFromSettings(ss);
//    a = uFact.create(Optional.empty(), Optional.empty(), true);
//
//    final String home = System.getProperty("user.home");
//
//    Optional.ofNullable(System.getenv("MAVEN_HOME")).orElse(home + "/.sdkman/candidates/maven/current/");
//
//    gavNoClassifier = new DefaultGAV("io.test:testArtifact:1.0.0:jar:");
//    cfNoClassifier = new DefaultGAV("io.test:testArtifact:1.0.0:cleanfuel:");
//    gavClassifier = new DefaultGAV("io.test:testArtifact:1.0.0:jar:class");
//  }
//
//  @Test
//  public void testGets() {
//
//  }
//}
