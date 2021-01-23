package doubletap.boopninja.doubletap.Utils;

import static java.lang.String.format;

import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;

public class DataFetcherHelper {

  public static String parseString(DataFetchingEnvironment environment, String key) {
    String value = environment.getArgument(key);
    if (value == null) {
      throw new GraphQLException(format("%s was null!", key));
    }
    return value;
  }
}
