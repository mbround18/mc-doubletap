package doubletap.boopninja.doubletap.Integrations.Base;

import doubletap.boopninja.doubletap.Authorizors.Base.Role;
import doubletap.boopninja.doubletap.Errors.RestrictedPermissionNode;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLFieldsContainer;
import graphql.schema.visibility.GraphqlFieldVisibility;
import java.util.List;

public class PolicySecurityFieldVisibility implements GraphqlFieldVisibility {

  private final Role role;

  public PolicySecurityFieldVisibility(Role role) {
    this.role = role;
  }

  @Override
  public List<GraphQLFieldDefinition> getFieldDefinitions(GraphQLFieldsContainer fieldsContainer) {
    return fieldsContainer.getFieldDefinitions();
  }

  @Override
  public GraphQLFieldDefinition getFieldDefinition(GraphQLFieldsContainer fieldsContainer, String fieldName) {
    String node = String.join(".", fieldsContainer.getName(), fieldName);
    GraphQLFieldDefinition definition = fieldsContainer.getFieldDefinition(fieldName);
    if (this.role.isBaseNode(node)) {
      return definition;
    }
    if (this.role.isAllowed(node)) {
      return definition;
    }
    throw new RestrictedPermissionNode(String.format("You do not have access to the permission node %s", node));
  }
}
