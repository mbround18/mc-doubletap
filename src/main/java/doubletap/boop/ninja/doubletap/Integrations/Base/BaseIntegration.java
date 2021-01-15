package doubletap.boop.ninja.doubletap.Integrations.Base;

import static doubletap.boop.ninja.doubletap.Doubletap.config;
import static java.lang.String.format;
import static spark.Spark.*;

import com.google.gson.Gson;
import doubletap.boop.ninja.doubletap.Authorizors.Base.Policy;
import doubletap.boop.ninja.doubletap.Integrations.Base.Interfaces.BaseIntegrationInterface;
import doubletap.boop.ninja.doubletap.Utils.FileResourceUtils;
import graphql.*;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

public class BaseIntegration implements BaseIntegrationInterface {

  private final TypeDefinitionRegistry typeRegistry;
  private final SchemaGenerator schemaGenerator;
  private final SessionManager sessionManager = new SessionManager();
  private final String schemaName;
  private final Gson formatter = new Gson();
  private final Logger logger;

  public BaseIntegration(String schemaName) {
    this.schemaName = schemaName;
    this.logger = LoggerFactory.getLogger(format("Doubletap.%s", schemaName));
    SchemaParser schemaParser = new SchemaParser();
    this.schemaGenerator = new SchemaGenerator();

    InputStream schema = FileResourceUtils.getFileFromResourceAsStream(format("schema/%s.graphql", schemaName));
    this.typeRegistry = schemaParser.parse(schema);

    exceptionMap();
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

  private String parseGraphqlPost(@NotNull Request request, @NotNull Response response) {
    sessionManager.createSessionIfNotExist(request);
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
    GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, wiring().Build(policies));
    GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
    ExecutionResult executionResult = build.execute(gqlRequest);

    /* Collect results and output data */
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

  public void Build() {
    String schemaPath = format("/%s", schemaName);
    logger.info(format("Enabling Integration %s", schemaName));
    path(schemaPath, () -> post("/graphql", this::parseGraphqlPost));
    logger.info(format("Enabled Integration %s", schemaName));
  }
}
