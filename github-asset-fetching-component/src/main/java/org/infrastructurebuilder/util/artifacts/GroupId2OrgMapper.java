package org.infrastructurebuilder.util.artifacts;

import java.util.Optional;
import java.util.function.Function;
/**
 * An instance of this maps a given "type" of groupId to the "correct" github org
 * The trivial reverse-map case is io.github.xxx as a groupId is the xxx org
 * The second trivial revers map is that xxx without any dots is the xxx org
 *
 * However, you might have abitrary additional mapping:
 *  "com.myorganization" -> "myorganization"
 *  "[org,com].myorganization.*" -> "myorganization" (possible collision!)
 *
 *  Return empty if the org doesn't map
 * @author mykel.alvis
 *
 */
@FunctionalInterface
public interface GroupId2OrgMapper extends Function<String, Optional<String>> {

}
