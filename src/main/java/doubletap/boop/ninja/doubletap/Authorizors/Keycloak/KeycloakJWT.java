package doubletap.boop.ninja.doubletap.Authorizors.Keycloak;

import doubletap.boop.ninja.doubletap.Doubletap;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class KeycloakJWT {

  public String iss;
  public String azp;
  public HashMap<String, HashMap<String, String[]>> resource_access;

  private URI keycloakHost() {
    String hostname = Doubletap.config.authorizerOptions.get("host");
    String realm = Doubletap.config.authorizerOptions.get("realm");
    return URI.create(String.format("%s/auth/realms/%s/protocol/openid-connect/userinfo", hostname, realm));
  }

  public boolean isValid(String fullJWT) {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().uri(keycloakHost()).header("Authorization", fullJWT).build();
    try {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      return response.statusCode() == 200;
    } catch (IOException | InterruptedException error) {
      if (Doubletap.config.debug) {
        error.printStackTrace();
      }
      return false;
    }
  }

  public String[] roles() {
    return this.resource_access.get(this.azp).get("roles");
  }
}
