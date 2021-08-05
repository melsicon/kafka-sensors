package de.melsicon.kafka.serde.confluent;

import dagger.assisted.AssistedFactory;
import de.melsicon.kafka.sensors.avro.SensorState;
import de.melsicon.kafka.sensors.avro.SensorStateWithDuration;
import de.melsicon.kafka.serde.SensorStateMapper;

@AssistedFactory
public interface SpecificSerdesFactory {
  SpecificSerdes create(SensorStateMapper<SensorState, SensorStateWithDuration> mapper);
}
