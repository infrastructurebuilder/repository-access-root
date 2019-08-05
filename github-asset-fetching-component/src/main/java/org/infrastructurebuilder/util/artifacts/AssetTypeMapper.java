package org.infrastructurebuilder.util.artifacts;

import java.util.function.BiFunction;

import org.kohsuke.github.GHAsset;

@FunctionalInterface
public interface AssetTypeMapper extends BiFunction<GAV,GHAsset, Boolean>{

}
