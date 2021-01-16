package doubletap.boopninja.doubletap.Authorizors;

import static doubletap.boopninja.doubletap.Doubletap.config;
import static doubletap.boopninja.doubletap.Doubletap.logger;
import static java.lang.String.format;

import doubletap.boopninja.doubletap.Authorizors.Base.Policy;
import doubletap.boopninja.doubletap.Authorizors.Discord.DiscordMe;
import doubletap.boopninja.doubletap.External.DiscordBot;
import doubletap.boopninja.doubletap.Utils.HttpHelper;
import java.net.http.HttpRequest;
import java.util.Arrays;

public class DiscordAuthorizer extends BaseAuthorizer {

  public Policy[] fetchRoles(String userId) {
    return Arrays
      .stream(DiscordBot.getRoleNames(userId))
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
