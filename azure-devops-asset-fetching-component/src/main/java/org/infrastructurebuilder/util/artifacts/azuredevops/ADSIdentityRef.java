
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


package org.infrastructurebuilder.util.artifacts.azuredevops;

import java.net.URL;
import java.util.Map;
import java.util.Optional;

import org.infrastructurebuilder.util.IBUtils;

/**
 * An Identity ref.  This is "owned" by an ADSOrg for convenience, but the owner value is irrelevant except to get a client
 * @author mykel.alvis
 *
 */
public interface ADSIdentityRef extends ADSNamedAndOwned<ADSOrg> {
  /**
   * This is REALLY dumb.  It is a map of name/value pairs that reference various items about an entity
   * See https://docs.microsoft.com/en-us/rest/api/azure/devops/release/releases/list?view=azure-devops-rest-5.1#identityref
   * @return Map of "links".  This is up to you to figure out with the really crappy ADS documentation
   */
  Map<String, String> getLinks();

  default Optional<URL> getAvatar() {
    return Optional.ofNullable(getLinks().get("avatar")).map(IBUtils::reURL);
  }

  /**
  descriptor  replated
  string
  The descriptor is the primary way to reference the graph subject while the system is running. This field will uniquely identify the same graph subject across both Accounts and Organizations.
  **/
  default Optional<String> getDescriptor() {
    return getDescription();
  }
  /**
  directoryAlias
  string
  Deprecated - Can be retrieved by querying the Graph user referenced in the "self" entry of the IdentityRef "_links" dictionary
  **/
  String getDirectoryAlias();
  /**
  displayName
  string
  This is the non-unique display name of the graph subject. To change this field, you must alter its value in the source provider.
  **/
  default String getDisplayName() {
    return getName();
  }
  /**
  id  // getId()
  string
  **/

  /**
  imageUrl
  string
  Deprecated - Available in the "avatar" entry of the IdentityRef "_links" dictionary
  **/
  @Deprecated
  Optional<URL> getImageUrl();
  /**
  inactive
  boolean
  Deprecated - Can be retrieved by querying the Graph membership state referenced in the "membershipState" entry of the GraphUser "_links" dictionary
  **/
  @Deprecated
  boolean isInactive();
  /**
  isAadIdentity
  boolean
  Deprecated - Can be inferred from the subject type of the descriptor (Descriptor.IsAadUserType/Descriptor.IsAadGroupType)
  **/
  @Deprecated
  boolean isAadIdentity();
  /**
  isContainer
  boolean
  Deprecated - Can be inferred from the subject type of the descriptor (Descriptor.IsGroupType)
  **/
  @Deprecated
  boolean isContainer();
  /**
  isDeletedInOrigin
  boolean
  **/
  boolean isDeletedInOrigin();
  /**
  profileUrl
  string
  Deprecated - not in use in most preexisting implementations of ToIdentityRef
  **/
  @Deprecated
  Optional<URL> getProfileUrl();
  /**
  uniqueName
  string
  Deprecated - use Domain+PrincipalName instead
  **/
  @Deprecated
  Optional<String> getUniqueName();
  /**
  url  // getUrl
  string
  This url is the full route to the source resource of this graph subject.
  **/


}
