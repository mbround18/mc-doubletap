package doubletap.boop.ninja.doubletap.Integrations.Server.Queries;

import static doubletap.boop.ninja.doubletap.External.MojangAPI.fetchPlayerById;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.PropertyDataFetcher;
import java.util.UUID;
import org.bukkit.OfflinePlayer;

public class OfflinePlayerFetcher {

  public static String playerName(DataFetchingEnvironment environment) {
    UUID id = PropertyDataFetcher.fetching(OfflinePlayer::getUniqueId).get(environment);
    String name = PropertyDataFetcher.fetching(OfflinePlayer::getName).get(environment);
    if (name == null) {
      return fetchPlayerById(id).name;
    }
    return PropertyDataFetcher.fetching(OfflinePlayer::getName).get(environment);
  }
}
