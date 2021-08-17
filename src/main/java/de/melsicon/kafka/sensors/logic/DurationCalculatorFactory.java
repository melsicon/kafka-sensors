package de.melsicon.kafka.sensors.logic;

import dagger.assisted.AssistedFactory;
import de.melsicon.kafka.sensors.model.SensorState;

@AssistedFactory
public interface DurationCalculatorFactory {
  DurationCalculator create(KVStore<String, SensorState> store);
}
