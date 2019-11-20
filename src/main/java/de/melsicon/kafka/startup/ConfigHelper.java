package de.melsicon.kafka.startup;

import io.helidon.config.Config;
import io.helidon.config.ConfigSources;
import java.nio.file.Path;

public final class ConfigHelper {
  private ConfigHelper() {}

  public static Config config(Path configFile) {
    try (var configSource = ConfigSources.file(configFile.toString()).build()) {
      return Config.builder(configSource)
          .disableSystemPropertiesSource()
          .disableEnvironmentVariablesSource()
          .build();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
