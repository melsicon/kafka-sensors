package de.melsicon.kafka.sensors.serde;

import com.google.common.collect.ImmutableMap;
import com.google.common.flogger.FluentLogger;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Provider;

public final class SerDesStore {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();
  private final ImmutableMap<String, Provider<SensorStateSerdes>> serdes;

  @Inject
  /* package */ SerDesStore(Map<String, Provider<SensorStateSerdes>> serdes) {
    this.serdes = ImmutableMap.copyOf(serdes);
  }

  public SensorStateSerdes serdes(String name) {
    if (!serdes.containsKey(name)) {
      logger.atWarning().log("Invalid parameter %s", name);
      logger.atInfo().log("Valid parameters are: %s", String.join(", ", serdes.keySet()));
      throw new NullPointerException(String.format("No serde for key %s", name));
    }
    return serdes.get(name).get();
  }
}
