package doubletap.boopninja.doubletap.Integrations.Server.Whitelist;

import doubletap.boopninja.doubletap.Integrations.Server.ServerFetcher;
import graphql.schema.PropertyDataFetcher;
import graphql.schema.idl.TypeRuntimeWiring;
import org.bukkit.Server;

public class WhitelistFetcher {

  public static TypeRuntimeWiring.Builder wiring(TypeRuntimeWiring.Builder builder) {
    return builder
      .dataFetcher("players", ServerFetcher::whiteListedPlayers)
      .dataFetcher("hasWhitelist", PropertyDataFetcher.fetching(Server::hasWhitelist));
  }
}
