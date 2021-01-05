package doubletap.boop.ninja.doubletap.Mutations;

import graphql.schema.DataFetchingEnvironment;
import org.bukkit.OfflinePlayer;

import static doubletap.boop.ninja.doubletap.Doubletap.logger;
import static java.lang.String.format;
import static org.bukkit.Bukkit.getOfflinePlayerIfCached;

public class WhitelistMutations {
    private static OfflinePlayer setWhitelistStatus(String name, Boolean status) {
        OfflinePlayer player = getOfflinePlayerIfCached(name);
        assert player != null;
        String logMessage;
        if (player.isWhitelisted() == status) {
            logger.info(format("%s whitelist status is unchanged", name));
            return player;
        }

        if (status) {
            logMessage = format("Added %s to whitelist", name);
        } else {
            logMessage = format("Removed %s from whitelist", name);
        }
        logger.info(logMessage);
        player.setWhitelisted(status);
        return player;
    }

    private static String parsePlayerName(DataFetchingEnvironment environment) {
        String playerName = environment.getArgument("name");
        assert playerName != null;
        return playerName;
    }

    public static OfflinePlayer add(DataFetchingEnvironment environment) {
        return setWhitelistStatus(parsePlayerName(environment), true);
    }

    public static OfflinePlayer remove(DataFetchingEnvironment environment) {
        return setWhitelistStatus(parsePlayerName(environment), false);
    }

}
