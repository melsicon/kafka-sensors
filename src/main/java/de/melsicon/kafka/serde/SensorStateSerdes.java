package de.melsicon.kafka.serde;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import org.apache.kafka.common.serialization.Serde;

public interface SensorStateSerdes {
  Format format();

  Serde<SensorState> createSensorStateSerde();

  Serde<SensorStateWithDuration> createSensorStateWithDurationSerde();
}
