package doubletap.boopninja.doubletap.Utils;

import static doubletap.boopninja.doubletap.Doubletap.logger;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public class FileResourceUtils {

  public static String PluginDirectory;

  public static InputStream getFileFromResourceAsStream(String fileName) {
    // The class loader that loaded the class
    ClassLoader classLoader = FileResourceUtils.class.getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(fileName);

    // the stream holding the file content
    if (inputStream == null) {
      throw new IllegalArgumentException("file not found! " + fileName);
    } else {
      return inputStream;
    }
  }

  public static void copyResourceToPluginDir(@NotNull String resourceName, @NotNull Boolean overwrite) {
    assert PluginDirectory != null;
    try {
      InputStream resourceFile = getFileFromResourceAsStream(resourceName);
      Path outputFile = new File(PluginDirectory, resourceName).toPath().toAbsolutePath();
      Files.createDirectories(outputFile.getParent());
      if (overwrite) {
        Files.copy(resourceFile, outputFile, StandardCopyOption.REPLACE_EXISTING);
      } else {
        try {
          Files.copy(resourceFile, outputFile);
        } catch (FileAlreadyExistsException e) {
          // Do nothing
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static <T> T pluginFileToClass(String path, Type typeOfT) {
    String filePath = Paths.get(PluginDirectory, path).toAbsolutePath().toString();
    String fileContent = readLineByLine(filePath);
    return new Gson().fromJson(fileContent, typeOfT);
  }

  public static boolean pluginFileExists(String path) {
    File tempFile = new File(Paths.get(PluginDirectory, path).toAbsolutePath().toString());
    return tempFile.exists();
  }

  private static String readLineByLine(String filePath) {
    StringBuilder contentBuilder = new StringBuilder();
    try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
      stream.forEach(s -> contentBuilder.append(s).append("\n"));
    } catch (UncheckedIOException | IOException e) {
      logger.error(String.format("Exception! %s%nIssue occurred when parsing: %s", e.getMessage(), filePath));
      e.printStackTrace();
    }
    return contentBuilder.toString();
  }
}
