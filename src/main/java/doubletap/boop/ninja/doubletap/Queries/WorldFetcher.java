package doubletap.boop.ninja.doubletap.Queries;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;

import static org.bukkit.Bukkit.getWorld;

public class WorldFetcher extends TypeRuntimeWiring.Builder {
    public static org.bukkit.World world(DataFetchingEnvironment environment) {
        String worldName = environment.getArgumentOrDefault("name", "world");
        return getWorld(worldName);
    }
}
