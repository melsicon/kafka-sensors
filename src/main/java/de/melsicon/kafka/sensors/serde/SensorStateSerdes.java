package de.melsicon.kafka.sensors.serde;

import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import org.apache.kafka.common.serialization.Serde;

public interface SensorStateSerdes {
  Format format();

  Serde<SensorState> createSensorStateSerde();

  Serde<SensorStateWithDuration> createSensorStateWithDurationSerde();
}
