package doubletap.boopninja.doubletap.Integrations.Server;

import static doubletap.boopninja.doubletap.Doubletap.config;
import static org.bukkit.Bukkit.getServer;

import doubletap.boopninja.doubletap.Integrations.Server.World.WorldFetcher;
import doubletap.boopninja.doubletap.Utils.HttpHelper;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.PropertyDataFetcher;
import graphql.schema.idl.TypeRuntimeWiring;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;

public class ServerFetcher {

  public static TypeRuntimeWiring.Builder wiring(TypeRuntimeWiring.Builder builder) {
    return builder
      .dataFetcher("world", WorldFetcher::world)
      .dataFetcher("ip", ServerFetcher::fetchPublicIP)
      .dataFetcher("whitelistedPlayers", ServerFetcher::whiteListedPlayers)
      .dataFetcher("hasWhitelist", PropertyDataFetcher.fetching(Server::hasWhitelist))
      .dataFetcher("getTPS", PropertyDataFetcher.fetching(Server::getTPS));
  }

  private static String fetchPublicIP(DataFetchingEnvironment environment) {
    String serverIp = config.getPreferredIp();
    if (serverIp.length() > 0) {
      return serverIp;
    }
    return HttpHelper.get("http://checkip.amazonaws.com").strip();
  }

  public static OfflinePlayer[] whiteListedPlayers(DataFetchingEnvironment environment) {
    return getServer().getWhitelistedPlayers().toArray(OfflinePlayer[]::new);
  }
}
