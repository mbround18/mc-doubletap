package doubletap.boopninja.doubletap.Errors;

import graphql.GraphQLException;

public class RestrictedPermissionNode extends GraphQLException {

  public RestrictedPermissionNode(String message) {
    super(message);
  }
}
