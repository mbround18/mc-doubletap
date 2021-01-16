package doubletap.boopninja.doubletap.Integrations.Server.Queries;

import static org.bukkit.Bukkit.getServer;

import graphql.schema.DataFetchingEnvironment;
import org.bukkit.OfflinePlayer;

public class ServerFetcher {

  public static OfflinePlayer[] whiteListedPlayers(DataFetchingEnvironment environment) {
    return getServer().getWhitelistedPlayers().toArray(OfflinePlayer[]::new);
  }
}
