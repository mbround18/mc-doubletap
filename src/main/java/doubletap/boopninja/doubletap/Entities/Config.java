package doubletap.boopninja.doubletap.Entities;

import static org.bukkit.Bukkit.getServer;

import java.util.HashMap;
import java.util.Locale;
import java.util.Optional;

public class Config {

  public int port = 8101;
  public boolean debug = false;
  public String defaultPolicyName = "generic";
  public String authorizer = "base";
  public HashMap<String, String> authorizerOptions;

  public String getPreferredIp() {
    String key = "DOUBLETAP_PREFERRED_IP";
    return Optional
      .ofNullable(System.getenv(key))
      .orElseGet(() -> getServer().getIp())
      .toLowerCase(Locale.ROOT)
      .strip();
  }

  public String getDefaultPolicyName() {
    String key = "DOUBLETAP_DEFAULT_POLICY_NAME";
    return Optional
      .ofNullable(System.getenv(key))
      .orElseGet(() -> this.defaultPolicyName)
      .toLowerCase(Locale.ROOT);
  }

  public String getAuthorizer() {
    String key = "DOUBLETAP_AUTHORIZER";
    return Optional
      .ofNullable(System.getenv(key))
      .orElseGet(() -> this.authorizer)
      .toLowerCase(Locale.ROOT);
  }

  public String getAuthorizerOption(String key) {
    return Optional.ofNullable(System.getenv(key)).orElseGet(() -> this.authorizerOptions.get(key));
  }
}
