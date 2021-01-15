package doubletap.boop.ninja.doubletap.Authorizors;

import static doubletap.boop.ninja.doubletap.Doubletap.config;

import com.google.gson.Gson;
import doubletap.boop.ninja.doubletap.Authorizors.Base.Policy;
import doubletap.boop.ninja.doubletap.Authorizors.Base.Role;
import doubletap.boop.ninja.doubletap.Utils.FileResourceUtils;

public class BaseAuthorizer {

  public Gson gson = new Gson();

  public Policy[] authenticate(Object context) {
    return fetchDefaultPolicies();
  }

  public Policy[] fetchDefaultPolicies() {
    return fetchPolicies(config.getDefaultPolicyName());
  }

  public Policy[] fetchPolicies(String roleName) {
    String rolePath = String.format("policies/%s.json", roleName);
    boolean exists = FileResourceUtils.pluginFileExists(rolePath);
    if (exists) {
      Role role = FileResourceUtils.pluginFileToClass(rolePath, Role.class);
      return role.policies;
    }
    throw new IllegalArgumentException(String.format("Role %s does not exist!", roleName));
  }
}
