package de.melsicon.kafka.serde.avromapper;

import static de.melsicon.kafka.sensors.generic.SensorStateSchema.ENUM_OFF;
import static de.melsicon.kafka.sensors.generic.SensorStateSchema.ENUM_ON;
import static de.melsicon.kafka.sensors.generic.SensorStateSchema.STATE_OFF;
import static de.melsicon.kafka.sensors.generic.SensorStateSchema.STATE_ON;

import de.melsicon.kafka.model.SensorState.State;
import org.apache.avro.generic.GenericEnumSymbol;

public final class GenericMapperHelper {
  private GenericMapperHelper() {}

  public static State stateMap(GenericEnumSymbol<?> state) {
    switch (state.toString()) {
      case STATE_OFF:
        return State.OFF;

      case STATE_ON:
        return State.ON;

      default:
        throw new IllegalArgumentException("Unexpected Enum value: " + state.toString());
    }
  }

  public static GenericEnumSymbol<?> stateUnmap(State state) {
    switch (state) {
      case OFF:
        return ENUM_OFF;

      case ON:
        return ENUM_ON;
    }

    throw new IllegalArgumentException("Unexpected State Enum: " + state);
  }
}
