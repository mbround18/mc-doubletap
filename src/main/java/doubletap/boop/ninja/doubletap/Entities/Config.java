package doubletap.boop.ninja.doubletap.Entities;

import java.util.HashMap;

public class Config {
    public int port;
    public boolean debug = false;

    public String authorizer = "base";
    public HashMap<String, String> authorizerOptions;

}
