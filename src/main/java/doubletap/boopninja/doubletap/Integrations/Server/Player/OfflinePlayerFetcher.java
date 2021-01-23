package doubletap.boopninja.doubletap.Integrations.Server.Player;

import doubletap.boopninja.doubletap.External.MojangAPI;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.PropertyDataFetcher;
import graphql.schema.idl.TypeRuntimeWiring;
import java.util.UUID;
import org.bukkit.OfflinePlayer;

public class OfflinePlayerFetcher {

  public static TypeRuntimeWiring.Builder wiring(TypeRuntimeWiring.Builder builder) {
    return BasePlayerFetcher.wiring(builder).dataFetcher("name", OfflinePlayerFetcher::playerName);
  }

  public static String playerName(DataFetchingEnvironment environment) {
    UUID id = PropertyDataFetcher.fetching(OfflinePlayer::getUniqueId).get(environment);
    String name = PropertyDataFetcher.fetching(OfflinePlayer::getName).get(environment);
    if (name == null) {
      return MojangAPI.fetchPlayerById(id).name;
    }
    return PropertyDataFetcher.fetching(OfflinePlayer::getName).get(environment);
  }
}
