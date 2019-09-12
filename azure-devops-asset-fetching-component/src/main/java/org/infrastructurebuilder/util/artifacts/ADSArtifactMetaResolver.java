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

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static org.infrastructurebuilder.IBException.cet;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.infrastructurebuilder.IBConstants;
import org.infrastructurebuilder.IBException;
import org.infrastructurebuilder.util.IBUtils;
import org.infrastructurebuilder.util.artifacts.azuredevops.ADSAsset;
import org.infrastructurebuilder.util.artifacts.azuredevops.ADSOrg;
import org.infrastructurebuilder.util.artifacts.azuredevops.ADSProject;
import org.infrastructurebuilder.util.artifacts.azuredevops.ADSRepo;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * a singleton component that produces a downloaded GAV, backed (probably) in the .cache directory of the repository.
 *
 *
 * @author mykel.alvis
 *
 */
@Named(IBConstants.AZUREDEVOPS)
@Singleton
public class ADSArtifactMetaResolver implements ArtifactMetaResolver {
  private final GroupId2OrgMapper orgMapper;
  private final VersionMapper versionMapper;
  private final ADSAssetTypeMapper atm;
  private final ADSClientSupplier ghs;

  @Inject
  public ADSArtifactMetaResolver(ADSClientSupplier ghs, Map<String, GroupId2OrgMapper> o2gid,
      Map<String, ADSAssetTypeMapper> atm, Map<String, VersionMapper> versionMappers) {
    this.ghs = requireNonNull(ghs);
    this.orgMapper = ofNullable(o2gid.get(getId()))
        .orElseThrow(() -> new IBException("No GroupId2OrgMapper named " + getId()));
    this.versionMapper = ofNullable(versionMappers.get(getId()))
        .orElseThrow(() -> new IBException("No VersionMapper named " + getId()));
    this.atm = ofNullable(atm.get(getId())).orElseThrow(() -> new IBException("No ADSAssetTypeMapper named " + getId()));

  }

  @Override
  public String getId() {
    return IBConstants.AZUREDEVOPS; // Override in other singletons for OTHER auth producer types
  }

  public final static Path fetchAsset(Optional<String> token, ADSAsset asset, Path outs) {
    // DOWNLOAD THE ASSET!
    return cet.withReturningTranslation(() -> {
      OkHttpClient client = new OkHttpClient();
      Request.Builder request = new Request.Builder().url(asset.getUrl());
      token.ifPresent(t -> request.addHeader("Authorization", "token " + t));
      try (Response response = client.newCall(request.build()).execute();
          OutputStream outstream = Files.newOutputStream(outs);
          InputStream ins = response.body().byteStream()) {
        IBUtils.copy(ins, outstream);
      }
      return outs;
    });
  }

  @Override
  public Optional<GAV> apply(GAV t) {
    if (requireNonNull(t).getFile().isPresent())
      return Optional.of(t); // First requirement fulfilled

    Path outs = cet.withReturningTranslation(
        () -> Files.createTempFile("ADS_" + t.getArtifactId() + t.getClassifier().map(c -> "-" + c).orElse("")
            + t.getVersion().map(v -> "-" + v).orElse(""), "." + t.getExtension()));

    String projectString = "getit";
    Optional<ADSRepo> k =  this.orgMapper.apply(t.getGroupId())
        .flatMap(orgId ->this.ghs.get().getOrganization(orgId))  // Got the Optional<ADSOrg>
        .flatMap(org -> org.getProject(projectString))  // Got the project
        .flatMap(repo -> repo.getRepository(t.getArtifactId()))  // got the Repo
        ;
        return k
        .flatMap(repo -> cet.withReturningTranslation(() -> this.versionMapper.apply(t)
            // Get the viable Taglist as a stream
            .stream().map(tag -> cet.withReturningTranslation(() -> ofNullable(repo.getReleaseByTagName(tag))))
            .filter(Optional::isPresent).map(Optional::get).findFirst()))
        .flatMap(r -> ofNullable(cet.withReturningTranslation(() -> r.getAssets()))).orElse(Collections.emptyList())
        // Get the stream out of the way
        .stream()
        // filter using the asset type mapper
        .filter(asset -> atm.apply(t, asset))
        // find first
        .findFirst()
        // this is the first (and only) asset
        .map(asset -> fetchAsset(this.ghs.getToken(), asset, outs))
        // Map it to a new GAV with the path set
        .map(t::withFile);
  }

}
