package doubletap.boopninja.doubletap.Integrations.Server.Queries;

import doubletap.boopninja.doubletap.External.MojangAPI;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.PropertyDataFetcher;
import java.util.UUID;
import org.bukkit.OfflinePlayer;

public class OfflinePlayerFetcher {

  public static String playerName(DataFetchingEnvironment environment) {
    UUID id = PropertyDataFetcher.fetching(OfflinePlayer::getUniqueId).get(environment);
    String name = PropertyDataFetcher.fetching(OfflinePlayer::getName).get(environment);
    if (name == null) {
      return MojangAPI.fetchPlayerById(id).name;
    }
    return PropertyDataFetcher.fetching(OfflinePlayer::getName).get(environment);
  }
}
