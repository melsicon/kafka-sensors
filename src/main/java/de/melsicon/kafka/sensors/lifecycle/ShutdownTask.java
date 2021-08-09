package de.melsicon.kafka.sensors.lifecycle;

import com.google.common.util.concurrent.ServiceManager;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

/* package */ final class ShutdownTask extends Thread {
  private final ServiceManager manager;

  private ShutdownTask(ServiceManager manager) {
    super("ShutdownTask");
    this.manager = manager;
  }

  /* package */ static void installShutdownTask(ServiceManager manager) {
    var shutdownTask = new ShutdownTask(manager);
    Runtime.getRuntime().addShutdownHook(shutdownTask);
  }

  @Override
  public void run() {
    shutdownServices();
  }

  @SuppressWarnings("SystemOut") // Logging system might already be flushed / shut down
  private void shutdownServices() {
    try {
      manager.stopAsync().awaitStopped(Duration.ofSeconds(5L));
    } catch (TimeoutException ignored) {
      System.err.println("Timed out stopping services");
    }
  }
}
