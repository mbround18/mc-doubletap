package doubletap.boop.ninja.doubletap.Integrations.Base;

import static doubletap.boop.ninja.doubletap.Doubletap.config;

import doubletap.boop.ninja.doubletap.Authorizors.Base.Policy;
import doubletap.boop.ninja.doubletap.Authorizors.BaseAuthorizer;
import doubletap.boop.ninja.doubletap.Authorizors.DiscordAuthorizer;
import doubletap.boop.ninja.doubletap.Authorizors.KeycloakAuthorizer;
import doubletap.boop.ninja.doubletap.Authorizors.NetlifyAuthorizer;
import java.util.Locale;
import spark.Request;

public class SessionManager {

  private BaseAuthorizer getAuthorizer() {
    String authorizerName = config.getAuthorizer();
    authorizerName = authorizerName.toLowerCase(Locale.ROOT);
    switch (authorizerName) {
      case "netlify":
        return new NetlifyAuthorizer();
      case "keycloak":
        return new KeycloakAuthorizer();
      case "discord":
        return new DiscordAuthorizer();
    }
    return new BaseAuthorizer();
  }

  private String parseToken(Request request) {
    // TODO: Refactor if a future authorizer requires a different context.
    String token = request.headers("authorization");
    if (token == null) token = request.headers("Authorization");
    return token;
  }

  public void createSession(Request request) {
    request.session(true);
    BaseAuthorizer authorizer = getAuthorizer();
    Policy[] policies = authorizer.authenticate(parseToken(request));
    if (policies == null) {
      policies = authorizer.fetchDefaultPolicies();
    }
    request.session().attribute("policies", policies);
  }

  public void destroySession(Request request) {
    request.session().invalidate();
  }

  public void createSessionIfNotExist(Request request) {
    String token = parseToken(request);
    Policy[] policies = request.session().attribute("policies");
    if (token != null && policies != null) {
      destroySession(request);
      policies = null;
    }
    if (policies == null) {
      createSession(request);
    }
  }
}
