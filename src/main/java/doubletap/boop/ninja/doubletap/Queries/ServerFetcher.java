package doubletap.boop.ninja.doubletap.Queries;

import graphql.schema.DataFetchingEnvironment;
import org.bukkit.OfflinePlayer;

import static org.bukkit.Bukkit.getServer;

public class ServerFetcher {

    public static OfflinePlayer[] whiteListedPlayers(DataFetchingEnvironment environment) {
        return getServer().getWhitelistedPlayers().toArray(OfflinePlayer[]::new);
    }

}
