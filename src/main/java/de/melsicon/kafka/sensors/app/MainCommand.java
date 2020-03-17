package de.melsicon.kafka.sensors.app;

import de.melsicon.kafka.context.MainComponent;
import de.melsicon.kafka.lifecycle.StartUpManager;
import io.helidon.config.Config;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "kafka-sensors", mixinStandardHelpOptions = true)
public final class MainCommand implements Callable<Integer> {
  @Option(
      names = {"-c", "--config"},
      paramLabel = "CONFIGURATION",
      description = "the configuration file")
  public Path configFile = Paths.get("conf", "application.yaml");

  private MainCommand() {}

  public static Callable<Integer> command() {
    return new MainCommand();
  }

  private static MainComponent createMainComponent(Config config) {
    return MainComponent.factory().newMainComponent(config);
  }

  private StartUpManager createStartUpManager() {
    var config = ConfigHelper.config(configFile);
    var main = createMainComponent(config);

    return main.startUpManager();
  }

  @Override
  public Integer call() throws Exception {
    var startUpManager = createStartUpManager();
    startUpManager.startUp();
    return 0;
  }
}
