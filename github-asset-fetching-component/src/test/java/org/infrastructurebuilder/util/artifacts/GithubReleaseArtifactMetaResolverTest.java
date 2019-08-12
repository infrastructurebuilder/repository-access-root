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
import static org.infrastructurebuilder.IBConstants.ID;
import static org.infrastructurebuilder.IBConstants.PASSWORD;
import static org.infrastructurebuilder.IBConstants.USERNAME;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.infrastructurebuilder.util.artifacts.impl.DefaultGAV;
import org.infrastructurebuilder.util.auth.DefaultAuthenticationProducerFactory;
import org.infrastructurebuilder.util.auth.DefaultIBAuthentication;
import org.infrastructurebuilder.util.auth.IBAuthentication;
import org.infrastructurebuilder.util.auth.IBAuthenticationProducer;
import org.infrastructurebuilder.util.auth.IBAuthenticationProducerFactory;
import org.infrastructurebuilder.util.auth.kohsuke.KohsukeGHAuthenticationProducer;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GithubReleaseArtifactMetaResolverTest extends AbstractGAFCTestingSetup {

  private GithubReleaseArtifactMetaResolver a;
  private KohsukeGHSupplier ghs;
  private Map<String, GroupId2OrgMapper> o2gid;
  private Map<String, AssetTypeMapper> atm;
  private Map<String, VersionMapper> versionMappers;
  private IBAuthenticationProducerFactory ibaf;
  private Set<IBAuthenticationProducer> writers;
  private IBAuthenticationProducer writer1;
  private GAV gav;
  private List<IBAuthentication> iBAuthentications;
  private DefaultIBAuthentication iba1, iba2;
  private Optional<String> additional;
  private JSONObject servers;

  @BeforeClass
  public static void before() throws Exception {
    superSetUpBeforeClass();
  }

  @Before
  public void setUp() throws Exception {
    superSetup();
    Path temp = getWps().get();
    servers = new JSONObject();
    servers.put("A", new JSONObject().put(ID, "A").put(USERNAME, "username").put(PASSWORD, "password"));
    gav = new DefaultGAV("x:y:1.0.0");
    writer1 = new KohsukeGHAuthenticationProducer();
    writers = new HashSet<>(Arrays.asList(writer1));
    iba1 = new DefaultIBAuthentication();
    iba1.setServerId("A");
    iba1.setTarget(GITHUB);//??
    iba1.setType(GITHUB);
    additional = Optional.of("blah");
    DefaultIBAuthentication.addJSON(iba1, servers, additional);
    iBAuthentications = Arrays.asList(DefaultIBAuthentication.addJSON(iba1, servers, additional));
    ibaf = new DefaultAuthenticationProducerFactory(writers);
    ibaf.setTemp(temp);
    ibaf.setAuthentications(iBAuthentications);
    ghs = new DefaultKohsukeGHSupplier(ibaf);
    o2gid = new HashMap<>();
    GroupId2OrgMapper omapper = new GithubIOGroupId2OrgMapper();
    o2gid.put(GITHUB, omapper);
    atm = new HashMap<>();
    AssetTypeMapper atmm = new GithubAssetTypeMapper();
    atm.put(GITHUB, atmm);
    versionMappers = new HashMap<>();
    VersionMapper vm = new GithubGenericReleaseVersionMapper();
    versionMappers.put(GITHUB, vm);
    a = new GithubReleaseArtifactMetaResolver(ghs, o2gid, atm, versionMappers);
  }

  @AfterClass
  public final static void cleanup() {
    superAfterClass();
  }

  @Test
  public void testApply() {
    Optional<GAV> ax = a.apply(gav);
  }

}
