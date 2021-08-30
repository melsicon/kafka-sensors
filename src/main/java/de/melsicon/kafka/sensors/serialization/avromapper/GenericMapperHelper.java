package de.melsicon.kafka.sensors.serialization.avromapper;

import static de.melsicon.kafka.sensors.type.avro.generic.SchemaHelper.ENUM_OFF;
import static de.melsicon.kafka.sensors.type.avro.generic.SchemaHelper.ENUM_ON;
import static de.melsicon.kafka.sensors.type.avro.generic.SchemaHelper.STATE_OFF;
import static de.melsicon.kafka.sensors.type.avro.generic.SchemaHelper.STATE_ON;

import de.melsicon.kafka.sensors.model.SensorState.State;
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
        throw new IllegalArgumentException("Unexpected Enum value: " + state);
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
