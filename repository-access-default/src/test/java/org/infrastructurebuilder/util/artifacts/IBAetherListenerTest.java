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

import static org.eclipse.aether.RepositoryEvent.EventType.ARTIFACT_DESCRIPTOR_INVALID;
import static org.eclipse.aether.RepositoryEvent.EventType.ARTIFACT_DESCRIPTOR_MISSING;
import static org.eclipse.aether.RepositoryEvent.EventType.ARTIFACT_INSTALLING;
import static org.eclipse.aether.RepositoryEvent.EventType.METADATA_INSTALLED;
import static org.eclipse.aether.RepositoryEvent.EventType.METADATA_INSTALLING;
import static org.eclipse.aether.RepositoryEvent.EventType.METADATA_INVALID;
import static org.eclipse.aether.RepositoryEvent.EventType.METADATA_RESOLVED;
import static org.eclipse.aether.transfer.TransferEvent.EventType.CORRUPTED;
import static org.eclipse.aether.transfer.TransferEvent.EventType.FAILED;
import static org.eclipse.aether.transfer.TransferEvent.EventType.INITIATED;
import static org.eclipse.aether.transfer.TransferEvent.EventType.PROGRESSED;
import static org.eclipse.aether.transfer.TransferEvent.EventType.STARTED;
import static org.eclipse.aether.transfer.TransferEvent.EventType.SUCCEEDED;
import static org.joor.Reflect.on;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositoryEvent;
import org.eclipse.aether.RepositoryEvent.EventType;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.metadata.DefaultMetadata;
import org.eclipse.aether.metadata.Metadata;
import org.eclipse.aether.metadata.Metadata.Nature;
import org.eclipse.aether.transfer.MetadataNotFoundException;
import org.eclipse.aether.transfer.TransferCancelledException;
import org.eclipse.aether.transfer.TransferEvent;
import org.eclipse.aether.transfer.TransferResource;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IBAetherListenerTest {
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  private IBListener al;

  private DefaultRepositorySystemSession drss;
  Logger log = LoggerFactory.getLogger(IBAetherListenerTest.class);

  @Before
  public void setUp() throws Exception {
    al = new IBListener();
    on(al).set("log", log);
    drss = new DefaultRepositorySystemSession();
  }

  @Test
  public void testArtifactDescriptorInvalidRepositoryEvent() {
    al.artifactDescriptorInvalid(getRepositoryEvent(ARTIFACT_DESCRIPTOR_INVALID, false));
  }

  @Test
  public void testArtifactDescriptorMissingRepositoryEvent() {
    al.artifactDescriptorMissing(getRepositoryEvent(ARTIFACT_DESCRIPTOR_MISSING, false));
  }

  @Test
  public void testArtifactInstallingRepositoryEvent() {
    al.artifactInstalling(getRepositoryEvent(ARTIFACT_INSTALLING, false));
  }

  @Test
  public void testMetadataInstalledRepositoryEvent() {
    al.metadataInstalled(getRepositoryEvent(METADATA_INSTALLED, false));
  }

  @Test
  public void testMetadataInstallingRepositoryEvent() {
    al.metadataInstalling(getRepositoryEvent(METADATA_INSTALLING, false));
  }

  @Test
  public void testMetadataInvalidRepositoryEvent() {
    al.metadataInvalid(getRepositoryEvent(METADATA_INVALID, false));
  }

  @Test
  public void testMetadataInvalidRepositoryEventFailed() {
    al.metadataInvalid(getRepositoryEvent(METADATA_INVALID, true));
  }

  @Test
  public void testMetadataResolvedRepositoryEvent() {
    al.metadataResolved(getRepositoryEvent(METADATA_RESOLVED, false));
  }

  @Test
  public void testMetadataResolvedWithExceptionRepositoryEvent() {
    al.metadataResolved(getRepositoryEvent(METADATA_RESOLVED, true));
  }

  @Test
  public void testTransferCorrupted() throws TransferCancelledException {
    al.transferCorrupted(getTransferEvent(CORRUPTED));
  }

  @Test
  public void testTransferFailed() {
    al.transferFailed(getTransferEvent(FAILED));
  }

  @Test
  public void testTransferInitiated() {
    al.transferInitiated(getTransferEvent(INITIATED));
  }

  @Test
  public void testTransferProgressed() throws TransferCancelledException {
    al.transferProgressed(getTransferEvent(PROGRESSED));
  }

  @Test
  public void testTransferStarted() {
    al.transferStarted(getTransferEvent(STARTED));
  }

  @Test
  public void testTransferSucceeded() {
    al.transferSucceeded(getTransferEvent(SUCCEEDED));
  }

  private Metadata getMetadata(final EventType e, final boolean failed) {
    final Metadata m = new DefaultMetadata("group", "artifact", "1,0.0", "type", Nature.RELEASE_OR_SNAPSHOT,
        new File("."));
    switch (e) {
    case METADATA_INVALID:
      if (failed) {
        m.setFile(null);
      }
      break;
    case ARTIFACT_DESCRIPTOR_INVALID:
      break;
    default:
      break;
    }
    return m;
  }

  private RepositoryEvent getRepositoryEvent(final EventType repositoryEventType, final boolean fail) {
    final RepositoryEvent x = on(RepositoryEvent.class).create(new RepositoryEvent.Builder(drss, repositoryEventType))
        .get();
    on(x).set("artifact", new DefaultArtifact("junit:junit:2.99"));
    List<Exception> exceptions;
    switch (repositoryEventType) {
    case METADATA_RESOLVED:
      on(x).set("metadata", getMetadata(repositoryEventType, fail));
      exceptions = fail ? Arrays.asList(new MetadataNotFoundException(x.getMetadata(), drss.getLocalRepository()))
          : Arrays.asList(new FileNotFoundException("blah"));

      on(x).set("exceptions", exceptions);
      break;
    case METADATA_INVALID:
      on(x).set("metadata", getMetadata(repositoryEventType, fail));
      exceptions = fail ? Arrays.asList(new MetadataNotFoundException(x.getMetadata(), drss.getLocalRepository()))
          : Arrays.asList(new FileNotFoundException("blah"));

      on(x).set("exceptions", exceptions);
      break;
    case ARTIFACT_DESCRIPTOR_INVALID:
      on(x).set("exceptions", Arrays.asList(new FileNotFoundException("TestException")));
      on(x).set("metadata", getMetadata(repositoryEventType, fail));
      break;
    default:
      break;
    }
    return x;
  }

  private TransferEvent getTransferEvent(final TransferEvent.EventType transferEventType) {
    final TransferResource res = new TransferResource("repo", "http://www.google.com", "test", new File("."), null);
    final TransferEvent x = on(TransferEvent.class).create(new TransferEvent.Builder(drss, res)).get();
    on(x).set("type", transferEventType);
    return x;
  }

}
