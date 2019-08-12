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

import static org.infrastructurebuilder.IBConstants.GITHUB;
import static org.junit.Assert.assertNotNull;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.maven.settings.building.SettingsBuilder;
import org.codehaus.plexus.ContainerConfiguration;
import org.codehaus.plexus.DefaultContainerConfiguration;
import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.PlexusConstants;
import org.codehaus.plexus.classworlds.ClassWorld;
import org.eclipse.sisu.space.SpaceModule;
import org.eclipse.sisu.space.URLClassSpace;
import org.eclipse.sisu.wire.WireModule;
import org.infrastructurebuilder.IBConstants;
import org.infrastructurebuilder.util.PropertiesSupplier;
import org.infrastructurebuilder.util.SettingsSupplier;
import org.infrastructurebuilder.util.auth.DefaultAuthenticationProducerFactory;
import org.infrastructurebuilder.util.auth.DefaultIBAuthentication;
import org.infrastructurebuilder.util.auth.IBAuthentication;
import org.infrastructurebuilder.util.auth.IBAuthenticationProducer;
import org.infrastructurebuilder.util.auth.kohsuke.KohsukeGHAuthenticationProducer;
import org.infrastructurebuilder.util.config.WorkingPathSupplier;
import org.json.JSONObject;

public abstract class AbstractGAFCTestingSetup {
  private static final String TESTING = "testing";
  private static Path target;
  private static Path badSettings;
  //  private static String gsval;
  private static Map<String, String> defaultEnv;
  private static Path nonSettings;
  private static Path localRepoSettings;
  private static Path noLocalRepoSettings;
  private static WorkingPathSupplier wps = new WorkingPathSupplier();

  public static void superSetUpBeforeClass() throws Exception {
    target = wps.getRoot();
    badSettings = target.resolve("test-classes").resolve("bad-settings.xml").toAbsolutePath();
    noLocalRepoSettings = target.resolve("test-classes").resolve("settings-no-local.xml").toAbsolutePath();
    localRepoSettings = target.resolve("test-classes").resolve("settings-with-local.xml").toAbsolutePath();
    nonSettings = target.resolve("test-classes").resolve("no-such-settings.xml").toAbsolutePath();
    defaultEnv = System.getenv();
  }

  public static void superAfterClass() {
    wps.finalize();
  }

  private DefaultPlexusContainer c;
  private SettingsSupplier s;
  //  private PropertiesSupplier p;
  private boolean isWindows;
  private org.codehaus.plexus.classworlds.ClassWorld kw;
  private ContainerConfiguration dpcreq;
  private SettingsBuilder dsb;
  private DefaultAuthenticationProducerFactory spi;
  private DefaultIBAuthentication a1;
  private List<IBAuthentication> authsGood;

  public void superSetup() throws Exception {

    KohsukeGHAuthenticationProducer iba = new KohsukeGHAuthenticationProducer();
    spi = new DefaultAuthenticationProducerFactory(new HashSet<>(Arrays.asList(iba)));
    spi.setTemp(wps.get());
    a1 = new DefaultIBAuthentication();
    a1.setTarget("east1");
    a1.setServerId("test");
    a1.setType(GITHUB);
    JSONObject servers = new JSONObject().put("test",
        new JSONObject().put(IBConstants.USERNAME, "user").put(IBConstants.PASSWORD, "pw"));
    a1 = (DefaultIBAuthentication) DefaultIBAuthentication.addJSON(a1, servers,
        Optional.of("https:://api.github.com/v2"));
    final DefaultIBAuthentication a2 = new DefaultIBAuthentication();
    a2.setTarget("east1");
    a2.setServerId("test");
    a2.setType(GITHUB);
    final DefaultIBAuthentication a3 = new DefaultIBAuthentication();
    a3.setType(GITHUB);
    a2.setTarget("east2");

    authsGood = Arrays.asList(a1);
    spi.setAuthentications(authsGood);

    isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
    //    final String mavenCoreRealmId = TESTING;
    //    kw = new ClassWorld(mavenCoreRealmId, getClass().getClassLoader());
    //
    //    dpcreq = new DefaultContainerConfiguration().setClassWorld(kw).setClassPathScanning(PlexusConstants.SCANNING_INDEX)
    //        .setName(TESTING);
    //    c = new DefaultPlexusContainer(dpcreq,
    //        new WireModule(new SpaceModule(new URLClassSpace(kw.getClassRealm(TESTING)))));
    //
    //    PropertiesSupplier theseProps = c.lookup(PropertiesSupplier.class);
    //    s = c.lookup(SettingsSupplier.class, "default");
    //    dsb = c.lookup(SettingsBuilder.class);
  }

  public DefaultAuthenticationProducerFactory getSpi() {
    return spi;
  }

  public static String getTesting() {
    return TESTING;
  }

  public static Path getTarget() {
    return target;
  }

  public static Path getBadSettings() {
    return badSettings;
  }

  public static Map<String, String> getDefaultEnv() {
    return defaultEnv;
  }

  public static Path getNonSettings() {
    return nonSettings;
  }

  public static Path getLocalRepoSettings() {
    return localRepoSettings;
  }

  public static Path getNoLocalRepoSettings() {
    return noLocalRepoSettings;
  }

  public static WorkingPathSupplier getWps() {
    return wps;
  }

  public DefaultPlexusContainer getContainer() {
    return c;
  }

  public SettingsSupplier getSettingsSupplier() {
    return s;
  }

  public boolean isWindows() {
    return isWindows;
  }

  public org.codehaus.plexus.classworlds.ClassWorld getKw() {
    return kw;
  }

  public ContainerConfiguration getDpcreq() {
    return dpcreq;
  }

  public SettingsBuilder getDsb() {
    return dsb;
  }

}
