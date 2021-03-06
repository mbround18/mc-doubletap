package doubletap.boopninja.doubletap.Integrations.Server.World;

import static org.bukkit.Bukkit.getWorld;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;

public class WorldFetcher extends TypeRuntimeWiring.Builder {

  public static org.bukkit.World world(DataFetchingEnvironment environment) {
    String worldName = environment.getArgumentOrDefault("name", "world");
    return getWorld(worldName);
  }
}
