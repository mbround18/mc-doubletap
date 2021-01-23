package doubletap.boopninja.doubletap.Integrations.Server.Player;

import graphql.schema.PropertyDataFetcher;
import graphql.schema.idl.TypeRuntimeWiring;
import org.bukkit.OfflinePlayer;

public class BasePlayerFetcher {

  public static TypeRuntimeWiring.Builder wiring(TypeRuntimeWiring.Builder builder) {
    return builder
      .dataFetcher("isWhitelisted", PropertyDataFetcher.fetching(OfflinePlayer::isWhitelisted))
      .dataFetcher("isOnline", PropertyDataFetcher.fetching(OfflinePlayer::isOnline))
      .dataFetcher("isBanned", PropertyDataFetcher.fetching(OfflinePlayer::isBanned));
  }
}
