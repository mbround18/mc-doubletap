package doubletap.boopninja.doubletap.Integrations.Server;

import static org.bukkit.Bukkit.getServer;

import doubletap.boopninja.doubletap.Authorizors.Base.Policy;
import doubletap.boopninja.doubletap.Integrations.Base.Interfaces.RuntimeWiringInterface;
import doubletap.boopninja.doubletap.Integrations.Server.Player.OfflinePlayerFetcher;
import doubletap.boopninja.doubletap.Integrations.Server.Player.OnlinePlayerFetcher;
import doubletap.boopninja.doubletap.Integrations.Server.Player.OnlinePlayerMutations;
import doubletap.boopninja.doubletap.Integrations.Server.Whitelist.WhitelistFetcher;
import doubletap.boopninja.doubletap.Integrations.Server.Whitelist.WhitelistMutations;
import doubletap.boopninja.doubletap.Integrations.Server.World.WorldFetcher;
import graphql.schema.PropertyDataFetcher;
import graphql.schema.idl.TypeRuntimeWiring;
import org.jetbrains.annotations.NotNull;

public class ServerRuntimeWiring implements RuntimeWiringInterface {

  @Override
  public graphql.schema.idl.RuntimeWiring Build(@NotNull Policy[] policies) {
    return newRuntimeWiringWithSecurity(policies)
      .type("QueryType", this::queryType)
      .type("MutationType", this::mutationType)
      .type("Server", ServerFetcher::wiring)
      .type("Player", OnlinePlayerFetcher::wiring)
      .type("OfflinePlayer", OfflinePlayerFetcher::wiring)
      .type("Location", this::locationType)
      .type("PlayerMutations", OnlinePlayerMutations::wiring)
      .type("WhitelistMutation", WhitelistMutations::wiring)
      .type("WhitelistQuery", WhitelistFetcher::wiring)
      .build();
  }

  private TypeRuntimeWiring.Builder queryType(TypeRuntimeWiring.Builder builder) {
    return builder
      .dataFetcher("server", environment -> getServer())
      .dataFetcher("world", WorldFetcher::world)
      .dataFetcher("player", OnlinePlayerFetcher::playerByName)
      .dataFetcher("whitelist", environment -> environment);
  }

  private TypeRuntimeWiring.Builder mutationType(TypeRuntimeWiring.Builder builder) {
    return builder
      .dataFetcher("whitelist", environment -> environment)
      .dataFetcher("player", environment -> environment);
  }

  private TypeRuntimeWiring.Builder locationType(TypeRuntimeWiring.Builder builder) {
    return builder
      .dataFetcher("x", PropertyDataFetcher.fetching("blockX"))
      .dataFetcher("y", PropertyDataFetcher.fetching("blockY"))
      .dataFetcher("z", PropertyDataFetcher.fetching("blockZ"));
  }
}
