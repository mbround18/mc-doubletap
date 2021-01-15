package doubletap.boop.ninja.doubletap.Authorizors;

import static doubletap.boop.ninja.doubletap.Doubletap.config;
import static doubletap.boop.ninja.doubletap.Doubletap.logger;
import static doubletap.boop.ninja.doubletap.External.DiscordBot.getRoleNames;
import static java.lang.String.format;

import doubletap.boop.ninja.doubletap.Authorizors.Base.Policy;
import doubletap.boop.ninja.doubletap.Authorizors.Discord.DiscordMe;
import doubletap.boop.ninja.doubletap.Utils.HttpHelper;
import java.net.http.HttpRequest;
import java.util.Arrays;

public class DiscordAuthorizer extends BaseAuthorizer {

  public Policy[] fetchRoles(String userId) {
    return Arrays
      .stream(getRoleNames(userId))
      .filter(
        roleName -> {
          try {
            this.fetchPolicies(roleName);
            return true;
          } catch (IllegalArgumentException e) {
            return false;
          }
        }
      )
      .map(this::fetchPolicies)
      .flatMap(Arrays::stream)
      .toArray(Policy[]::new);
  }

  public String fetchUserId(String token) {
    String baseUrl = "https://discord.com/api/v8";
    HttpRequest.Builder httpRequest = HttpHelper.request(format("%s/users/@me", baseUrl));
    if (config.debug) {
      logger.info(format("[DiscordAPI][Token]: %s", token));
    }
    httpRequest.header("Authorization", token);
    DiscordMe discordMe = gson.fromJson(HttpHelper.sendRequest(httpRequest.build()), DiscordMe.class);
    return discordMe.id;
  }

  @Override
  public Policy[] authenticate(Object context) {
    if (context == null || context.toString() == null) {
      return null;
    }
    String userId = fetchUserId(context.toString());
    return fetchRoles(userId);
  }
}
