package doubletap.boopninja.doubletap.Integrations.Server.Player;

import static doubletap.boopninja.doubletap.Doubletap.logger;
import static doubletap.boopninja.doubletap.Doubletap.runTask;
import static doubletap.boopninja.doubletap.Utils.DataFetcherHelper.parseString;
import static java.lang.String.format;
import static org.bukkit.Bukkit.getPlayer;
import static org.bukkit.Bukkit.getServer;

import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import java.util.Locale;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class OnlinePlayerMutations {

  public static TypeRuntimeWiring.Builder wiring(TypeRuntimeWiring.Builder builder) {
    return builder
      .dataFetcher("ban", OnlinePlayerMutations::ban)
      .dataFetcher("kick", OnlinePlayerMutations::kick)
      .dataFetcher("setFoodLevel", OnlinePlayerMutations::setFoodLevel)
      .dataFetcher("setLevel", OnlinePlayerMutations::setLevel)
      .dataFetcher("setHealth", OnlinePlayerMutations::setHealth)
      .dataFetcher("setGameMode", OnlinePlayerMutations::setGameMode);
  }

  private static Player getPlayerByName(DataFetchingEnvironment environment) {
    String name = parseString(environment, "name");
    Player player = getServer().getPlayer(name);
    if (player == null) {
      throw new GraphQLException(format("Player %s not found!", name));
    }
    return player;
  }

  public static GameMode parseGameMode(String gamemode) {
    if (gamemode == null) {
      throw new GraphQLException(
        "No valid gamemode provided! Please use one of ADVENTURE|CREATIVE|SPECTATOR|SURVIVAL"
      );
    }
    switch (gamemode.toUpperCase()) {
      case "ADVENTURE":
        return GameMode.ADVENTURE;
      case "CREATIVE":
        return GameMode.CREATIVE;
      case "SPECTATOR":
        return GameMode.SPECTATOR;
      default:
        return GameMode.SURVIVAL;
    }
  }

  public static Player ban(DataFetchingEnvironment environment) {
    Player player = getPlayerByName(environment);
    String reason = parseString(environment, "reason");
    if (reason.length() == 0) {
      logger.error("Please provide a reason to ban this player!");
    } else {
      runTask(
        () -> {
          String message = format("§c%s §6has been banned: §c%s", player.getName(), reason);
          player.sendMessage(message);
          player.banPlayer(reason);
          logger.info(message);
        }
      );
    }
    return player;
  }

  public static Player kick(DataFetchingEnvironment environment) {
    Player player = getPlayerByName(environment);
    String reason = parseString(environment, "reason");
    if (reason.length() == 0) {
      logger.error("Please provide a reason to kick this player!");
    } else {
      runTask(
        () -> {
          String message = format("§c%s §6has been kicked: §c%s", player.getName(), reason);
          player.sendMessage(message);
          player.kickPlayer(reason);
          logger.info(message);
        }
      );
    }

    return player;
  }

  public static Player setGameMode(DataFetchingEnvironment environment) {
    String name = parseString(environment, "name");
    String gamemode = parseString(environment, "gamemode");
    Player player = getPlayer(name);
    if (player == null) {
      throw new GraphQLException(format("Player %s not found!", name));
    }
    runTask(
      () -> {
        String message = format(
          "§6%s gamemode changed to §c%s",
          name,
          gamemode.toLowerCase(Locale.ROOT)
        );
        player.setGameMode(parseGameMode(gamemode));
        player.sendMessage(message);
        logger.info(message);
      }
    );
    return player;
  }

  public static Player setFoodLevel(DataFetchingEnvironment environment) {
    int foodLevel = environment.getArgument("foodLevel");
    Player player = getPlayerByName(environment);
    runTask(
      () -> {
        player.setFoodLevel(foodLevel);
        String message = format(
          "§c%s §6food level has been set to §c%s",
          player.getName(),
          foodLevel
        );
        player.sendMessage(message);
        logger.info(message);
      }
    );
    return player;
  }

  public static Player setLevel(DataFetchingEnvironment environment) {
    int level = environment.getArgument("level");
    Player player = getPlayerByName(environment);
    runTask(
      () -> {
        player.setLevel(level);
        String message = format("§c%s §6level has been set to §c%s", player.getName(), level);
        player.sendMessage(message);
        logger.info(message);
      }
    );
    return player;
  }

  public static Player setHealth(DataFetchingEnvironment environment) {
    int amount = environment.getArgument("health");
    Player player = getPlayerByName(environment);
    runTask(
      () -> {
        if (!player.isDead()) {
          player.setHealth(amount);
        }
        String message = "";
        switch (amount) {
          case 20:
            message = format("§c%s §6has been healed!", player.getName());
            break;
          case 0:
            message = format("§c%s §6has been killed!", player.getName());
            break;
          default:
            message = format("§c%s §6health has been set to §c%s", player.getName(), amount);
            break;
        }
        player.sendMessage(message);
        logger.info(message);
      }
    );
    return player;
  }
}
