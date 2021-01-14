package doubletap.boop.ninja.doubletap.External;

import static doubletap.boop.ninja.doubletap.Doubletap.config;
import static doubletap.boop.ninja.doubletap.Doubletap.logger;
import static java.lang.String.format;
import static spark.Spark.get;

import graphql.GraphQLException;
import java.util.List;
import java.util.Objects;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;

public class DiscordBot {

  private static final Boolean enabled = "discord".equals(config.authorizer);
  private static final String token = config.getAuthorizerOption("DISCORD_BOT_TOKEN");
  private static final String guildId = config.getAuthorizerOption("DISCORD_SERVER_ID");

  public static JDA client;

  public static void setClient(JDA client) {
    DiscordBot.client = client;
  }

  public static void start() {
    if (enabled) {
      logger.info("[DiscordBot]: Initializing");
      try {
        setClient(JDABuilder.createDefault(token).build());
        String inviteUrl = client.getInviteUrl(Permission.getFromOffset(3072));

        get(
          "/discord/bot",
          (request, response) -> {
            response.redirect(inviteUrl);
            return null;
          }
        );
        logger.info("[DiscordBot]: Logged in");
      } catch (LoginException error) {
        logger.info("[DiscordBot]: Failed to log in!");
      }
    }
  }

  private static Guild fetchGuild() {
    if (guildId == null) {
      throw new GraphQLException("Please provide the Guild ID!");
    }
    Guild guild = client.getGuildById(guildId);
    if (guild == null) {
      throw new GraphQLException(
        "Guild not found! Its possible the bot is not linked to your server. Try http://127.0.0.1/discord/bot"
      );
    }
    return guild;
  }

  private static User fetchUser(String userId) {
    if (userId == null) {
      throw new GraphQLException("User ID not found! this should not happen.");
    }
    User user = client.retrieveUserById(userId).complete();
    if (user == null) {
      throw new GraphQLException("User was not found! Was the wrong user ID supplied?");
    }
    return user;
  }

  private static Member fetchMember(Guild guild, User user) {
    if (guild == null) {
      throw new GraphQLException("Guild provided is null! This should not happen!");
    }
    if (user == null) {
      throw new GraphQLException("User provided is null! This should not happen!");
    }
    Member member = guild.retrieveMember(user).complete();
    if (member == null) {
      String errorMessage = format("%s was not found! Do you belong to the %s?", user.getName(), guild.getName());
      throw new GraphQLException(errorMessage);
    }
    return member;
  }

  public static String[] getRoleNames(String userId) {
    if (!enabled) {
      throw new GraphQLException("DiscordBot is not enabled!");
    }

    Guild guild = fetchGuild();
    User user = fetchUser(userId);
    Member member = fetchMember(guild, user);
    List<Role> roles = member.getRoles();
    return Objects
      .requireNonNull(roles)
      .stream()
      .map(Role::getName)
      .map(String::toLowerCase)
      .map(roleName -> roleName.replaceAll("[^A-Za-z0-9]", "-"))
      .toArray(String[]::new);
  }
}
