package doubletap.boopninja.doubletap.Integrations.Server.Whitelist;

import static doubletap.boopninja.doubletap.Doubletap.logger;
import static doubletap.boopninja.doubletap.Doubletap.runTask;
import static doubletap.boopninja.doubletap.External.MojangAPI.fetchPlayerByName;
import static java.lang.String.format;
import static org.bukkit.Bukkit.getOfflinePlayer;
import static org.bukkit.Bukkit.getServer;

import doubletap.boopninja.doubletap.External.Mojang.PlayerInfo;
import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;

public class WhitelistMutations {

  public static TypeRuntimeWiring.Builder wiring(TypeRuntimeWiring.Builder builder) {
    return builder
      .dataFetcher("status", WhitelistMutations::setWhitelistStatus)
      .dataFetcher("add", WhitelistMutations::add)
      .dataFetcher("remove", WhitelistMutations::remove);
  }

  private static Server setWhitelistStatus(DataFetchingEnvironment environment) {
    Boolean status = environment.getArgumentOrDefault("status", false);
    Server server = getServer();
    runTask(
      () -> {
        server.setWhitelist(status);
        String statusMessage = "off";
        if (status) {
          statusMessage = "on";
        }
        logger.info(format("Whitelist has been toggled %s", statusMessage));
      }
    );
    return server;
  }

  private static OfflinePlayer setUserWhitelistStatus(String name, Boolean status) {
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
    return setUserWhitelistStatus(parsePlayerName(environment), true);
  }

  public static OfflinePlayer remove(DataFetchingEnvironment environment) {
    return setUserWhitelistStatus(parsePlayerName(environment), false);
  }
}
