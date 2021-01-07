package doubletap.boop.ninja.doubletap.Queries;

import graphql.schema.DataFetchingEnvironment;
import org.bukkit.OfflinePlayer;

import static doubletap.boop.ninja.doubletap.Doubletap.logger;
import static org.bukkit.Bukkit.getServer;

public class ServerFetcher {

    public static OfflinePlayer[] whiteListedPlayers(DataFetchingEnvironment environment) {
        OfflinePlayer[] players = getServer().getWhitelistedPlayers().toArray(OfflinePlayer[]::new);
        for (OfflinePlayer player : players) {
            logger.info(player.getUniqueId().toString());
            logger.info(player.getName());
        }
        return players;
    }


}
