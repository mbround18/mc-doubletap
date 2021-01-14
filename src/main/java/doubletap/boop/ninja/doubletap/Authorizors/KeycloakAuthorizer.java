package doubletap.boop.ninja.doubletap.Authorizors;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import doubletap.boop.ninja.doubletap.Authorizors.Base.Policy;
import doubletap.boop.ninja.doubletap.Authorizors.Keycloak.KeycloakJWT;
import graphql.GraphQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Stream;

public class KeycloakAuthorizer extends BaseAuthorizer {

  private KeycloakJWT parseJwt(String jwt) {
    DecodedJWT decodedJWT = JWT.decode(jwt);
    String decodedPayload = new String(Base64.getDecoder().decode(decodedJWT.getPayload()));
    return gson.fromJson(decodedPayload, KeycloakJWT.class);
  }

  @Override
  public Policy[] authenticate(Object context) {
    String jwt = context.toString();
    if (!jwt.startsWith("Bearer")) {
      throw new GraphQLException("Token did not include prefix!");
    }
    jwt = jwt.substring(7);
    KeycloakJWT jwtParsed = parseJwt(jwt);
    boolean isValid = jwtParsed.isValid(context.toString());
    if (!isValid) {
      throw new GraphQLException("The provided token is invalid!");
    }
    Stream<String> roles = Arrays.stream(jwtParsed.roles());
    Stream<Policy[]> policies = roles.map(this::fetchPolicies);
    return policies.flatMap(Arrays::stream).toArray(Policy[]::new);
  }
}
