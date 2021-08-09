package de.melsicon.kafka.sensors.serde;

import org.immutables.value.Value;

@Value.Style(allParameters = true)
@Value.Immutable(builder = false)
public abstract class NamedSerDes implements WithNamedSerDes {
  /* package */ NamedSerDes() {}

  public static NamedSerDes of(String name, SensorStateSerdes serdes) {
    return ImmutableNamedSerDes.of(name, serdes);
  }

  public abstract String name();

  public abstract SensorStateSerdes serdes();
}
