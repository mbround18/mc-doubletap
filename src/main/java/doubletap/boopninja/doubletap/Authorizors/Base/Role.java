package doubletap.boopninja.doubletap.Authorizors.Base;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Role {

  public Policy[] policies;
  private final List<String> baseNodes = Arrays.asList(
    "__Schema.*",
    "__Type.*",
    "__InputValue.*",
    "__Directive.*",
    "__Field.*",
    "__EnumValue.*",
    "^QueryType.*",
    "^MutationType.*"
  );

  private Policy[] filterPoliciesByEffect(String effect) {
    String effectLower = effect.toLowerCase(Locale.ROOT);
    Stream<Policy> policyStream = Stream.of(this.policies);
    Stream<Policy> filteredPolicyStream = policyStream.filter(
      policy -> policy.effect.toLowerCase(Locale.ROOT).equals(effectLower)
    );
    return filteredPolicyStream.toArray(Policy[]::new);
  }

  public boolean isBaseNode(String node) {
    return (
      this.baseNodes.stream()
        .filter(baseNode -> Pattern.compile(baseNode).matcher(node).matches())
        .toArray()
        .length >
      0
    );
  }

  public boolean isAllowed(String node) {
    boolean allowedNodesCheck =
      Arrays
        .stream(allowedPolicies())
        .filter(policy -> policy.matchesAttribute(node))
        .toArray()
        .length >
      0;
    if (allowedNodesCheck) {
      boolean deniedNodesCheck =
        Arrays
          .stream(deniedPolicies())
          .filter(policy -> policy.matchesAttribute(node))
          .toArray()
          .length >
        0;
      return !deniedNodesCheck;
    }
    return false;
  }

  private Policy[] allowedPolicies() {
    return this.filterPoliciesByEffect("allow");
  }

  private Policy[] deniedPolicies() {
    return this.filterPoliciesByEffect("deny");
  }
}
