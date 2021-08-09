package de.melsicon.kafka.sensors.lifecycle;

import com.google.common.util.concurrent.ServiceManager;
import javax.inject.Inject;

public final class StartUpManager {
  private final ServiceManager manager;

  @Inject
  /* package */ StartUpManager(ServiceManager manager) {
    this.manager = manager;
  }

  public void startUp() {
    manager.startAsync().awaitHealthy();
    ShutdownTask.installShutdownTask(manager);
  }

  public void shutDown() {
    manager.stopAsync().awaitStopped();
  }
}
