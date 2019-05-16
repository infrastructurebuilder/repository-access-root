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

package org.infrastructurebuilder.util.artifacts.io.xpp3;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.infrastructurebuilder.util.artifacts.model.RepositoryAccessConfigInputSource;
import org.infrastructurebuilder.util.artifacts.model.RepositoryAccessConfiguration;
import org.infrastructurebuilder.util.artifacts.model.io.xpp3.IBRAManagerXpp3Reader;
import org.infrastructurebuilder.util.artifacts.model.io.xpp3.IBRAManagerXpp3ReaderEx;
import org.infrastructurebuilder.util.artifacts.model.io.xpp3.IBRAManagerXpp3Writer;
import org.infrastructurebuilder.util.config.WorkingPathSupplier;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



public class IBRAConfigurationReaderTest {

  private WorkingPathSupplier wps = new WorkingPathSupplier();
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  private RepositoryAccessConfiguration config;
  private RepositoryAccessConfiguration config2;
  private Path target;

  @Before
  public void setUp() throws Exception {
    target = wps.get();
    Path tFle = target.resolve(UUID.randomUUID().toString() + ".xml");
    try (InputStream ins = getClass().getResourceAsStream("/testRemoteAccessConfig.xml")) {
      final IBRAManagerXpp3Reader a = new IBRAManagerXpp3Reader();
      config = a.read(ins);

    }
    try (OutputStream stream = Files.newOutputStream(tFle)) {
      final IBRAManagerXpp3Writer b = new IBRAManagerXpp3Writer();
      b.write(stream, config);
    }
    try (BufferedReader ins = Files.newBufferedReader(tFle)) {
      final IBRAManagerXpp3ReaderEx a = new IBRAManagerXpp3ReaderEx();
      final RepositoryAccessConfigInputSource is = new RepositoryAccessConfigInputSource();
      config2 = a.read(ins, true, is);

    }


  }

  @Test
  public void test() {
    assertNotNull(config);
    assertNotNull(config2);

  }

}
