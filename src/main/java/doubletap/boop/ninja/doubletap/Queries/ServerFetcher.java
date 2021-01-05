package doubletap.boop.ninja.doubletap.Queries;

import graphql.schema.DataFetchingEnvironment;

import static org.bukkit.Bukkit.getServer;

public class ServerFetcher {

    public static Object[] whiteListedPlayers(DataFetchingEnvironment environment) {
        return getServer().getWhitelistedPlayers().toArray();
    }


}
