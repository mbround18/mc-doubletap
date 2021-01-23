package doubletap.boopninja.doubletap.Integrations.Server.Player;

import static org.bukkit.Bukkit.getPlayer;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.PropertyDataFetcher;
import graphql.schema.idl.TypeRuntimeWiring;
import org.bukkit.entity.Player;

public class OnlinePlayerFetcher {

  public static TypeRuntimeWiring.Builder wiring(TypeRuntimeWiring.Builder builder) {
    return BasePlayerFetcher
      .wiring(builder)
      .dataFetcher("name", PropertyDataFetcher.fetching("playerListName"))
      .dataFetcher("gamemode", PropertyDataFetcher.fetching(Player::getGameMode));
  }

  public static Player playerByName(DataFetchingEnvironment environment) {
    String playerName = environment.getArgument("name");
    assert playerName != null;
    return getPlayer(playerName);
  }
}
