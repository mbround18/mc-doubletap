package doubletap.boop.ninja.doubletap.Controllers;

import static doubletap.boop.ninja.doubletap.Doubletap.config;
import static doubletap.boop.ninja.doubletap.Doubletap.logger;
import static spark.Spark.*;

import com.google.gson.Gson;
import doubletap.boop.ninja.doubletap.Authorizors.Base.Policy;
import doubletap.boop.ninja.doubletap.Authorizors.BaseAuthorizer;
import doubletap.boop.ninja.doubletap.Authorizors.DiscordAuthorizer;
import doubletap.boop.ninja.doubletap.Authorizors.KeycloakAuthorizer;
import doubletap.boop.ninja.doubletap.Authorizors.NetlifyAuthorizer;
import doubletap.boop.ninja.doubletap.Controllers.Graphql.LocalRuntimeWiring;
import doubletap.boop.ninja.doubletap.Utils.FileResourceUtils;
import graphql.*;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.jetbrains.annotations.NotNull;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.mustache.MustacheTemplateEngine;

import java.io.InputStream;
import java.util.*;

public class GraphqlController {

  private final Gson formatter = new Gson();
  private final SchemaGenerator schemaGenerator;
  private final TypeDefinitionRegistry typeRegistry;

  public GraphqlController() {
    SchemaParser schemaParser = new SchemaParser();
    this.schemaGenerator = new SchemaGenerator();

    InputStream schema = FileResourceUtils.getFileFromResourceAsStream("schema.gql");
    this.typeRegistry = schemaParser.parse(schema);

    routeMap();
    exceptionMap();
  }

  private void routeMap() {
    get("", this::parseGraphqlGet, new MustacheTemplateEngine());
    post("", this::parseGraphqlPost);
  }

  private void exceptionMap() {
    exception(IllegalArgumentException.class, this::castExceptionAsGraphqlErrors);
    exception(IllegalStateException.class, this::castExceptionAsGraphqlErrors);
    exception(graphql.AssertException.class, this::castExceptionAsGraphqlErrors);
    exception(GraphQLException.class, this::castExceptionAsGraphqlErrors);
  }

  private void castExceptionAsGraphqlErrors(Exception exception, Request request, Response response) {
    Map<String, Object> results = new HashMap<>();
    ArrayList<String> errors = new ArrayList<>();
    errors.add(exception.toString());
    results.put("errors", errors);
    response.body(new Gson().toJson(results));
    if (config.debug) {
      exception.printStackTrace();
    }
  }

  private BaseAuthorizer getAuthorizer() {
    String authorizerName = config.authorizer.toLowerCase(Locale.ROOT);
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

  private void createSessionIfNotExist(Request request) {
    // TODO: Refactor if a future authorizer requires a different context.
    String token = request.headers("authorization");
    if (token == null) token = request.headers("Authorization");

    Policy[] policies = request.session().attribute("policies");

    if (token != null && policies != null) {
      request.session().invalidate();
      policies = null;
    }

    if (policies == null) {
      request.session(true);
      BaseAuthorizer authorizer = getAuthorizer();
      request.session().attribute("policies", authorizer.authenticate(token));
    }
  }

  public ModelAndView parseGraphqlGet(@NotNull Request request, @NotNull Response response) {
    createSessionIfNotExist(request);
    return new ModelAndView(new HashMap<String, String>(), "graphiql.html");
  }

  public String parseGraphqlPost(@NotNull Request request, @NotNull Response response) {
    createSessionIfNotExist(request);
    response.type("application/json");

    Policy[] policies = request.session().attribute("policies");
    if (policies == null) {
      response.status(401);
      throw new IllegalArgumentException("No policies loaded! Are you missing authorization?");
    }

    // Cast request body as ExecutionInput
    ExecutionInput.Builder gqlRequest = formatter.fromJson(request.body(), ExecutionInput.Builder.class);

    logGraphQLRequest(gqlRequest);

    // Generate schema build and execute it for results
    RuntimeWiring wiring = new LocalRuntimeWiring().Build(policies);
    GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, wiring);
    GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
    ExecutionResult executionResult = build.execute(gqlRequest);

    // Collect results and output data
    Map<String, Object> results = new HashMap<>();
    results.put("data", executionResult.getData());
    List<GraphQLError> errors = executionResult.getErrors();
    if (!errors.isEmpty()) {
      results.put("errors", errors);
    }
    return new Gson().toJson(results);
  }

  private void logGraphQLRequest(ExecutionInput.Builder request) {
    ExecutionInput content = request.build();
    List<String> messages = new ArrayList<>();
    if (content.getOperationName() != null) {
      if (content.getOperationName().equals("IntrospectionQuery")) {
        return;
      }
      String operationName = String.format("[Operation Name]: %s", content.getOperationName());
      messages.add(operationName);
    }
    if (content.getQuery() != null) {
      String query = String.format("[Query|Mutation] ~ %n%s", content.getQuery());
      messages.add(query);
    }
    if (content.getVariables() != null) {
      String variables = String.format("[Variables]: %s", new Gson().toJson(content.getVariables()));
      messages.add(variables);
    }
    logger.debug(String.join("\n", messages));
  }
}
