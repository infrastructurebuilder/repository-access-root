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

import java.util.function.BiFunction;

import org.infrastructurebuilder.util.artifacts.azuredevops.ADSAsset;

/** Return true if the given ADSAsset is what is desired based on the provided GAV.
 * This allows us to parse the list of assets for a given org and match them based
 * on (generally regex) criteria.  That is probably not a perfect method, but
 * the default code has already matched based on a required version, so it'll
 * work nearly all the time.
 *
 *
 * @author mykel.alvis
 *
 */
public interface ADSAssetTypeMapper extends BiFunction<GAV, ADSAsset, Boolean>, IdentifiedAndWeighted {

}
