package doubletap.boop.ninja.doubletap.Utils;

import graphql.GraphQLException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.lang.String.format;

public class HttpHelper {
    public static HttpClient client() {
        return HttpClient.newHttpClient();
    }
    public static HttpRequest request(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
    }

    public static String get(String url) {
        try {
            HttpResponse<String> response =
                    client().send(request(url), HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException error) {
            throw new GraphQLException(format("HTTP error for %s", url));
        }
    }

    public static String get(String url, String errorMessage) {
        try {
            HttpResponse<String> response =
                    client().send(request(url), HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException error) {
            throw new GraphQLException(errorMessage);
        }
    }
}
