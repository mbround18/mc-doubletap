package doubletap.boop.ninja.doubletap.External.Mojang;

import java.math.BigInteger;
import java.util.UUID;

public class PlayerInfo {

  public String id;
  public String name;
  public Long changedToAt = 0L;

  public UUID idToUUID() {
    return new UUID(
      new BigInteger(this.id.substring(0, 16), 16).longValue(),
      new BigInteger(this.id.substring(16), 16).longValue()
    );
  }
}
