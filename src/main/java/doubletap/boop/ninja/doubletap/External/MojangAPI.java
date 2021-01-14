package doubletap.boop.ninja.doubletap.External;

import static java.lang.String.format;

import com.google.gson.Gson;
import doubletap.boop.ninja.doubletap.External.Mojang.PlayerInfo;
import doubletap.boop.ninja.doubletap.Utils.HttpHelper;
import java.util.Arrays;
import java.util.Comparator;
import java.util.UUID;

public class MojangAPI {

  public static PlayerInfo fetchPlayerById(UUID id) {
    String url = format("https://api.mojang.com/user/profiles/%s/names", id.toString());
    String response = HttpHelper.get(url, format("Player %s not found!", id));
    PlayerInfo[] playerInfos = new Gson().fromJson(response, PlayerInfo[].class);
    Arrays.sort(playerInfos, Comparator.comparingLong(p -> p.changedToAt));
    return playerInfos[playerInfos.length - 1];
  }

  public static PlayerInfo fetchPlayerByName(String name) {
    String url = format("https://api.mojang.com/users/profiles/minecraft/%s", name);
    String response = HttpHelper.get(url, format("Player %s not found!", name));
    return new Gson().fromJson(response, PlayerInfo.class);
  }
}
