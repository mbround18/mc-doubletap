package doubletap.boop.ninja.doubletap.Utils;

import static doubletap.boop.ninja.doubletap.Doubletap.config;
import static doubletap.boop.ninja.doubletap.Doubletap.logger;
import static java.lang.String.format;

import graphql.GraphQLException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpHelper {

  public static HttpClient client() {
    return HttpClient.newHttpClient();
  }

  public static HttpRequest.Builder request(String url) {
    return HttpRequest.newBuilder().uri(URI.create(url));
  }

  private static HttpResponse.BodyHandler<String> handler() {
    return HttpResponse.BodyHandlers.ofString();
  }

  private static String parseResponse(HttpRequest request, HttpResponse<String> response) {
    if (config.debug) {
      logger.info(String.format("%s %s %n%s", request.uri().toString(), response.statusCode(), response.body()));
    }
    if (response.statusCode() != 200) {
      throw new GraphQLException(format("[DiscordAPI][%s]: %s", response.statusCode(), response.body()));
    }
    return response.body();
  }

  public static String sendRequest(HttpRequest request) {
    try {
      HttpResponse<String> response = client().send(request, handler());
      return parseResponse(request, response);
    } catch (IOException | InterruptedException error) {
      throw new GraphQLException(format("HTTP error for %s", request.uri()));
    }
  }

  public static String sendRequest(HttpRequest request, String errorMessage) {
    try {
      HttpResponse<String> response = client().send(request, handler());
      return parseResponse(request, response);
    } catch (IOException | InterruptedException error) {
      throw new GraphQLException(errorMessage);
    }
  }

  public static String get(String url) {
    HttpRequest req = request(url).build();
    return sendRequest(req);
  }

  public static String get(String url, String errorMessage) {
    HttpRequest req = request(url).build();
    return sendRequest(req, errorMessage);
  }
}
