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

import java.io.FileNotFoundException;

import javax.inject.Named;
import javax.inject.Singleton;

import org.codehaus.plexus.component.annotations.Component;
import org.eclipse.aether.AbstractRepositoryListener;
import org.eclipse.aether.RepositoryEvent;
import org.eclipse.aether.transfer.MetadataNotFoundException;
import org.eclipse.aether.transfer.TransferCancelledException;
import org.eclipse.aether.transfer.TransferEvent;
import org.eclipse.aether.transfer.TransferListener;
import org.eclipse.sisu.Typed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named("specific-aether-listener")
@Singleton
@Typed(TransferListener.class)
class IBListener extends AbstractRepositoryListener implements TransferListener {

  private Logger log;

  @Override
  public void artifactDescriptorInvalid(final RepositoryEvent event) {
    getLogger().warn(
        "The POM for " + event.getArtifact() + " is invalid"
            + ", transitive dependencies (if any) will not be available: " + event.getException().getMessage(),
        event.getException());
  }

  @Override
  public void artifactDescriptorMissing(final RepositoryEvent event) {
    getLogger().warn("The POM for " + event.getArtifact() + " is missing, no dependency information available");
  }

  @Override
  public void artifactInstalling(final RepositoryEvent event) {

    getLogger().info("Installing " + event.getArtifact().getFile() + " to " + event.getFile());
  }

  @Override
  public void metadataInstalling(final RepositoryEvent event) {
    getLogger().info("Installing " + event.getMetadata() + " to " + event.getFile());
  }

  @Override
  public void metadataInvalid(final RepositoryEvent event) {
    final Exception exception = event.getException();

    final StringBuilder buffer = new StringBuilder(256);
    buffer.append("The metadata ");
    if (event.getMetadata().getFile() != null) {
      buffer.append(event.getMetadata().getFile());
    } else {
      buffer.append(event.getMetadata());
    }

    if (exception instanceof FileNotFoundException) {
      buffer.append(" is inaccessible");
    } else {
      buffer.append(" is invalid");
    }

    if (exception != null) {
      buffer.append(": ");
      buffer.append(exception.getMessage());
    }

    getLogger().warn(buffer.toString(), exception);
  }

  @Override
  public void metadataResolved(final RepositoryEvent event) {
    final Exception e = event.getException();
    if (e != null)
      if (e instanceof MetadataNotFoundException) {
        getLogger().debug(e.getMessage());
      } else {
        getLogger().warn(e.getMessage(), e);
      }
  }

  @Override
  public void transferCorrupted(final TransferEvent event) throws TransferCancelledException {
    getLogger().warn("Transfer Corrupted for " + event.getResource().toString());
  }

  @Override
  public void transferFailed(final TransferEvent event) {
    getLogger().warn("Transfer Failed for " + event.getResource().toString());
  }

  @Override
  public void transferInitiated(final TransferEvent transferEvent) {
    getLogger().debug("Transfer initiated for " + transferEvent.getResource().toString());

  }

  @Override
  public void transferProgressed(final TransferEvent event) throws TransferCancelledException {
    getLogger().debug("Transfer Progressed for " + event.getResource().toString());
  }

  @Override
  public void transferStarted(final TransferEvent transferEvent) {
    getLogger().debug("Transfer Started for " + transferEvent.getResource().toString());

  }

  @Override
  public void transferSucceeded(final TransferEvent event) {
    getLogger().debug("Transfer Succeeded for " + event.getResource().toString());

  }

  private Logger getLogger() {
    if (log == null) {
      log = LoggerFactory.getLogger(IBListener.class);
    }

    return log;
  }

}
