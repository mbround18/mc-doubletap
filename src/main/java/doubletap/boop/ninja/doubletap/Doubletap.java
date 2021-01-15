package doubletap.boop.ninja.doubletap;

import static java.lang.String.format;
import static spark.Spark.*;

import com.google.gson.Gson;
import doubletap.boop.ninja.doubletap.Entities.Config;
import doubletap.boop.ninja.doubletap.External.DiscordBot;
import doubletap.boop.ninja.doubletap.Integrations.Base.BaseIntegration;
import doubletap.boop.ninja.doubletap.Integrations.Server.ServerIntegration;
import doubletap.boop.ninja.doubletap.Utils.FileResourceUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Locale;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Doubletap extends JavaPlugin {

  public static Config config = null;
  public static final Logger logger = LoggerFactory.getLogger("Doubletap");
  private final HashMap<String, Boolean> featureFlags = new HashMap<>();

  private void startupWebserver(int portNumber) {
    staticFiles.externalLocation(FileResourceUtils.PluginDirectory);
    // Plugin startup logic
    logger.info("Doubletap!! Thwack Thwack!! Initializing web server!");
    port(portNumber); // Spark will run on port 8080
    enableCors();
    get("/", (req, res) -> getServer().getMotd());
    get("/feature-flags", ((request, response) -> new Gson().toJson(featureFlags)));
    notFound(
      (req, res) -> {
        res.type("application/json");
        res.status(404);
        return "{\"message\":\"Not Found!\"}";
      }
    );

    // Using Route
    internalServerError(
      (req, res) -> {
        res.type("application/json");
        return "{\"message\":\"500 internal server error\"}";
      }
    );

    logger.info(format("Server Initialized! Navigate to http://127.0.0.1:%s/graphql", portNumber));
  }

  private BaseIntegration loadLocalIntegration(final String klassName) {
    try {
      String klassPath = format("doubletap.boop.ninja.doubletap.Integrations.%s.%sIntegration", klassName, klassName);
      return (BaseIntegration) Class.forName(klassPath).getDeclaredConstructor().newInstance();
    } catch (
      InstantiationException
      | IllegalAccessException
      | ClassNotFoundException
      | NoSuchMethodException
      | InvocationTargetException e
    ) {
      if (config.debug) {
        logger.error(format("Integration for %s not found!", klassName));
      }
      return null;
    }
  }

  private void loadIntegrations() {
    new ServerIntegration().Build();
    featureFlags.put("server", true);

    Plugin[] plugins = getServer().getPluginManager().getPlugins();
    for (Plugin plugin : plugins) {
      String name = plugin.getName();
      if (name.equals("Doubletap")) {
        continue;
      }
      BaseIntegration klass = this.loadLocalIntegration(name);
      if (klass != null) {
        featureFlags.put(name, true);
        klass.Build();
      }
    }
  }

  private void setupPluginFiles() {
    String configPath = "config.json";
    if (!getDataFolder().exists()) {
      if (getDataFolder().mkdir()) {
        logger.info("Created Doubletap plugin directory");
      }
    }
    FileResourceUtils.PluginDirectory = getDataFolder().getPath();
    String overwriteEnvVar = System.getenv("MC_DOUBLETAP_OVERWRITE");
    Boolean overwrite = false;
    if (overwriteEnvVar != null) {
      overwrite = "true".equals(overwriteEnvVar.toLowerCase(Locale.ROOT));
    }
    FileResourceUtils.copyResourceToPluginDir(configPath, overwrite);
    Doubletap.config = FileResourceUtils.pluginFileToClass(configPath, Config.class);

    FileResourceUtils.copyResourceToPluginDir("policies/admin.json", overwrite);
    FileResourceUtils.copyResourceToPluginDir("policies/generic.json", overwrite);
    FileResourceUtils.copyResourceToPluginDir("schema/config.schema.json", overwrite);
  }

  @Override
  public void onEnable() {
    logger.info("-".repeat(50));
    int pluginId = 9717;

    // Initiate project files
    setupPluginFiles();

    // Initiate metrics
    try {
      new Metrics(this, pluginId);
    } catch (ExceptionInInitializerError error) {
      // Ignore metrics errors
    }

    // Initiate webserver
    logger.info(format("Loaded config %n%s", new Gson().toJson(config)));
    startupWebserver(config.port);
    loadIntegrations();

    // Start bot if needed, will not start if you dont have discord as an authorizer
    DiscordBot.start();
    logger.info("-".repeat(50));
  }

  @Override
  public void onDisable() {
    logger.info("-".repeat(50));
    logger.info("Stopping web server");
    stop();
    awaitStop();
    logger.info("Web server stopped");
    logger.info("-".repeat(50));
  }

  public void enableCors() {
    options(
      "/*",
      (request, response) -> {
        String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");

        if (accessControlRequestHeaders != null) {
          response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
        }

        String accessControlRequestMethod = request.headers("Access-Control-Request-Method");

        if (accessControlRequestMethod != null) {
          response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
        }

        return "OK";
      }
    );
    before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
  }
}
