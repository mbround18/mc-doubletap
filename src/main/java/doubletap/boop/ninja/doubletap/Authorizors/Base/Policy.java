package doubletap.boop.ninja.doubletap.Authorizors.Base;

import java.util.Arrays;
import java.util.regex.Pattern;

public class Policy {

  public String name;
  public String effect;
  public String[] attributes;

  public boolean matchesAttribute(String node) {
    return (
      Arrays.stream(attributes).filter(patternNode -> this.validateRegex(patternNode, node)).toArray().length > 0
    );
  }

  private boolean validateRegex(String patternNode, String node) {
    return Pattern.compile(patternNode).matcher(node).matches();
  }
}
