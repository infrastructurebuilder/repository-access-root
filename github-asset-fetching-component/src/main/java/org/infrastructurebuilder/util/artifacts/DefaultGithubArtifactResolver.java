package org.infrastructurebuilder.util.artifacts;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static org.infrastructurebuilder.IBException.cet;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.inject.Named;

import org.infrastructurebuilder.IBException;
import org.kohsuke.github.GHAsset;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

@Named
public class DefaultGithubArtifactResolver implements GithubArtifactResolver {

  final private KohsukeGHSupplier ghs;
  final private AssetTypeMapper atm;
  final private GroupId2OrgMapper g2o;

  @Inject
  public DefaultGithubArtifactResolver(KohsukeGHSupplier ghs, GroupId2OrgMapper o2gid, AssetTypeMapper atm) {
    this.ghs = requireNonNull(ghs);
    this.g2o = requireNonNull(o2gid);
    this.atm = requireNonNull(atm);
  }

  @Override
  public Optional<GAV> getFromGithubReleases(GAV source) {
    if (requireNonNull(source).getVersion().isPresent()) {
      Optional<String> ooid = this.g2o.apply(source.getGroupId());
      if (ooid.isPresent()) {
        final GitHub gh = ghs.get();
        final GHOrganization org = cet.withReturningTranslation(() -> gh.getOrganization(ooid.get()));
        String ver = source.getVersion().get();
        GHRepository repo = cet.withReturningTranslation(() -> org.getRepository(source.getArtifactId()));
        // Convert to VersionMapper eventually
        List<String> taglist = Arrays.asList(ver, "v" + ver, source.getArtifactId() + "-" + ver);
        for (String tag : taglist) {
          Optional<GHRelease> rel = ofNullable(cet.withReturningTranslation(() -> repo.getReleaseByTagName(tag)));
          if (rel.isPresent()) {
            GHRelease ghrel = rel.get();
            List<GHAsset> ass = cet.withReturningTranslation(() -> ghrel.getAssets());
            Optional<GAV> v = ass.stream().filter(a -> this.atm.apply(source, a)).findFirst().map(asset -> {
              Path target = cet.withReturningTranslation(() -> Files.createTempFile("GHASSET", ".zip"));
              /**
               * This is the one so fetch the asset
               */
              URL u = asset.getUrl();
              return source.withFile(target);
            });
            return v;
          }
        }
      }
    }
    return Optional.empty();

  }
}
