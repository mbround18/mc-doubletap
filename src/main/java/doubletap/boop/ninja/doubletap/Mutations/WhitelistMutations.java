package doubletap.boop.ninja.doubletap.Mutations;

import static doubletap.boop.ninja.doubletap.Doubletap.logger;
import static doubletap.boop.ninja.doubletap.External.MojangAPI.fetchPlayerByName;
import static java.lang.String.format;
import static org.bukkit.Bukkit.getOfflinePlayer;

import doubletap.boop.ninja.doubletap.External.Mojang.PlayerInfo;
import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;
import org.bukkit.OfflinePlayer;

public class WhitelistMutations {

  private static OfflinePlayer setWhitelistStatus(String name, Boolean status) {
    PlayerInfo playerInfo = fetchPlayerByName(name);
    if (playerInfo == null) {
      throw new GraphQLException(format("Player %s not found!", name));
    }
    OfflinePlayer player = getOfflinePlayer(playerInfo.idToUUID());
    if (player.isWhitelisted() == status) {
      logger.info(format("%s whitelist status is unchanged", name));
      return player;
    }
    String logMessage;
    if (status) {
      logMessage = format("Added %s to whitelist", name);
    } else {
      logMessage = format("Removed %s from whitelist", name);
    }
    logger.info(logMessage);
    player.setWhitelisted(status);
    return player;
  }

  private static String parsePlayerName(DataFetchingEnvironment environment) {
    String playerName = environment.getArgument("name");
    if (playerName == null) {
      throw new IllegalArgumentException("No player name provided!");
    }
    return playerName;
  }

  public static OfflinePlayer add(DataFetchingEnvironment environment) {
    return setWhitelistStatus(parsePlayerName(environment), true);
  }

  public static OfflinePlayer remove(DataFetchingEnvironment environment) {
    return setWhitelistStatus(parsePlayerName(environment), false);
  }
}
