package doubletap.boopninja.doubletap.Integrations.Base.Interfaces;

import doubletap.boopninja.doubletap.Authorizors.Base.Policy;
import doubletap.boopninja.doubletap.Authorizors.Base.Role;
import doubletap.boopninja.doubletap.Integrations.Base.PolicySecurityFieldVisibility;
import graphql.schema.idl.RuntimeWiring;
import org.jetbrains.annotations.NotNull;

public interface RuntimeWiringInterface {
  RuntimeWiring Build(@NotNull Policy[] policies);

  default RuntimeWiring.Builder newRuntimeWiringWithSecurity(@NotNull Policy[] policies) {
    Role configuredRole = new Role();
    configuredRole.policies = policies;
    PolicySecurityFieldVisibility cfv = new PolicySecurityFieldVisibility(configuredRole);
    return RuntimeWiring.newRuntimeWiring().fieldVisibility(cfv);
  }
}
