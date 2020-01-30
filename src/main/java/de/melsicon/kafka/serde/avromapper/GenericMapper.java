package de.melsicon.kafka.serde.avromapper;

import static de.melsicon.kafka.sensors.generic.SensorStateSchema.ENUM_OFF;
import static de.melsicon.kafka.sensors.generic.SensorStateSchema.ENUM_ON;
import static de.melsicon.kafka.sensors.generic.SensorStateSchema.FIELD_ID;
import static de.melsicon.kafka.sensors.generic.SensorStateSchema.FIELD_STATE;
import static de.melsicon.kafka.sensors.generic.SensorStateSchema.FIELD_TIME;
import static de.melsicon.kafka.sensors.generic.SensorStateSchema.STATE_OFF;
import static de.melsicon.kafka.sensors.generic.SensorStateSchema.STATE_ON;
import static de.melsicon.kafka.sensors.generic.SensorStateWithDurationSchema.FIELD_DURATION;
import static de.melsicon.kafka.sensors.generic.SensorStateWithDurationSchema.FIELD_EVENT;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorState.State;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.generic.SensorStateSchema;
import de.melsicon.kafka.sensors.generic.SensorStateWithDurationSchema;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import org.apache.avro.generic.GenericData.EnumSymbol;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;

public final class GenericMapper implements AvroMapper<GenericRecord, GenericRecord> {
  public static GenericMapper instance() {
    return new GenericMapper();
  }

  private static State stateMap(EnumSymbol state) {
    switch (state.toString()) {
      case STATE_OFF:
        return State.OFF;

      case STATE_ON:
        return State.ON;

      default:
        throw new IllegalArgumentException("Unexpected Enum value: " + state.toString());
    }
  }

  private static EnumSymbol stateUnmap(State state) {
    switch (state) {
      case OFF:
        return ENUM_OFF;

      case ON:
        return ENUM_ON;
    }

    throw new IllegalArgumentException("Unexpected State Enum: " + state);
  }

  @Nullable
  @Override
  public SensorState map(@Nullable GenericRecord sensorState) {
    if (sensorState == null) {
      return null;
    }
    return SensorState.builder()
        .setId(((CharSequence) sensorState.get(FIELD_ID)).toString())
        .setTime(Instant.ofEpochMilli((Long) sensorState.get(FIELD_TIME)))
        .setState(stateMap((EnumSymbol) sensorState.get(FIELD_STATE)))
        .build();
  }

  @Nullable
  @Override
  public GenericRecord unmap(@Nullable SensorState sensorState) {
    if (sensorState == null) {
      return null;
    }
    return new GenericRecordBuilder(SensorStateSchema.SCHEMA)
        .set(FIELD_ID, sensorState.getId())
        .set(FIELD_TIME, sensorState.getTime().toEpochMilli())
        .set(FIELD_STATE, stateUnmap(sensorState.getState()))
        .build();
  }

  @Nullable
  @Override
  public SensorStateWithDuration map2(@Nullable GenericRecord sensorState) {
    if (sensorState == null) {
      return null;
    }
    var event = Objects.requireNonNull(map((GenericRecord) sensorState.get(FIELD_EVENT)));
    return SensorStateWithDuration.builder()
        .setEvent(event)
        .setDuration(Duration.ofMillis((Long) sensorState.get(FIELD_DURATION)))
        .build();
  }

  @Nullable
  @Override
  public GenericRecord unmap2(@Nullable SensorStateWithDuration sensorState) {
    if (sensorState == null) {
      return null;
    }
    var event = Objects.requireNonNull(unmap(sensorState.getEvent()));
    return new GenericRecordBuilder(SensorStateWithDurationSchema.SCHEMA)
        .set(FIELD_EVENT, event)
        .set(FIELD_DURATION, sensorState.getDuration().toMillis())
        .build();
  }
}
