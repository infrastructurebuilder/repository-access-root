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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.aether.util.artifact.JavaScopes;
import org.infrastructurebuilder.util.IBUtils;
import org.infrastructurebuilder.util.artifacts.impl.DefaultGAV;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class IBRAMvnCtrlTest {

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  private String pLocal;

  ArtifactServices utils;

  @Before
  public void setUp() throws Exception {
    pLocal = Paths.get(System.getProperty("user.home"), ".m2", "repository").toAbsolutePath().toString();
    final Path target = Paths.get(".").resolve("target").toAbsolutePath();
    final Path pp = target.resolve("testrepo").toAbsolutePath();
    IBUtils.deletePath(pp);
    assertFalse(pp.toString() + " does not exist ", Files.isDirectory(pp));
    Files.createDirectories(pp);
    assertTrue(pp.toString() + " does exist ", Files.isDirectory(pp));
    pLocal = pp.toString();

    utils = new DefaultRepositoryAccess(pLocal, null, null, ArtifactServices.CENTRAL_REPO_URL.toExternalForm(), false);
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testGetTrmeote() {
    assertEquals(ArtifactServices.CENTRAL_REPO_URL, utils.getRemoteRepo().get().toExternalForm());
  }

  @Test
  public void testGetArtifact() {
    final GAV c = new DefaultGAV("junit", "junit", null, "4.12", "jar");
    final GAV l = utils.getArtifact(c, JavaScopes.COMPILE);
    assertNotNull(l);
    assertTrue("File exists", l.getFile().get().toFile().exists());
  }

}
