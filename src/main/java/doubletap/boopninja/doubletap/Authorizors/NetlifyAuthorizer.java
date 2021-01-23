package doubletap.boopninja.doubletap.Authorizors;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import doubletap.boopninja.doubletap.Authorizors.Base.Policy;
import doubletap.boopninja.doubletap.Authorizors.Netlify.NetlifyJWT;
import java.util.Arrays;

public class NetlifyAuthorizer extends BaseAuthorizer {

  public Policy[] authenticate(Object context) {
    if (context == null || context.toString() == null) {
      return null;
    }
    DecodedJWT jwt = JWT.decode(context.toString());
    NetlifyJWT payload = new Gson().fromJson(jwt.getPayload(), NetlifyJWT.class);
    return (Policy[]) Arrays
      .stream(payload.roles)
      .map(this::fetchPolicies)
      .flatMap(Arrays::stream)
      .toArray();
  }
}
