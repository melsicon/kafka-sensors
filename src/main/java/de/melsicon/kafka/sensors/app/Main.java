package de.melsicon.kafka.sensors.app;

import com.google.common.flogger.FluentLogger;
import picocli.CommandLine;

public final class Main {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  private Main() {}

  public static void main(String... args) {
    var status = new CommandLine(MainCommand.command()).execute(args);
    if (status != 0) {
      logger.atSevere().log("\u001B[31mTerminating with status %d\u001B[0m\n", status);
      System.exit(status);
    }
  }
}
