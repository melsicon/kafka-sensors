package de.melsicon.kafka.serde.avromapper;

import static de.melsicon.kafka.sensors.generic.SensorStateSchema.FIELD_ID;
import static de.melsicon.kafka.sensors.generic.SensorStateSchema.FIELD_STATE;
import static de.melsicon.kafka.sensors.generic.SensorStateSchema.FIELD_TIME;
import static de.melsicon.kafka.sensors.generic.SensorStateWithDurationSchema.FIELD_DURATION;
import static de.melsicon.kafka.sensors.generic.SensorStateWithDurationSchema.FIELD_EVENT;
import static de.melsicon.kafka.serde.avromapper.GenericMapperHelper.stateMap;
import static de.melsicon.kafka.serde.avromapper.GenericMapperHelper.stateUnmap;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.generic.SensorStateSchema;
import de.melsicon.kafka.sensors.generic.SensorStateWithDurationSchema;
import de.melsicon.kafka.serde.SensorStateMapper;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import org.apache.avro.generic.GenericEnumSymbol;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.checkerframework.checker.nullness.qual.AssertNonNullIfNonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class GenericMapper implements SensorStateMapper<GenericRecord, GenericRecord> {
  private GenericMapper() {}

  public static GenericMapper instance() {
    return new GenericMapper();
  }

  @Override
  @AssertNonNullIfNonNull("sensorState")
  @Nullable
  public SensorState map(@Nullable GenericRecord sensorState) {
    if (sensorState == null) {
      return null;
    }
    return SensorState.builder()
        .setId((String) sensorState.get(FIELD_ID))
        .setTime((Instant) sensorState.get(FIELD_TIME))
        .setState(stateMap((GenericEnumSymbol<?>) sensorState.get(FIELD_STATE)))
        .build();
  }

  @Override
  @AssertNonNullIfNonNull("sensorState")
  @Nullable
  public GenericRecord unmap(@Nullable SensorState sensorState) {
    if (sensorState == null) {
      return null;
    }
    return new GenericRecordBuilder(SensorStateSchema.SCHEMA)
        .set(FIELD_ID, sensorState.getId())
        .set(FIELD_TIME, sensorState.getTime())
        .set(FIELD_STATE, stateUnmap(sensorState.getState()))
        .build();
  }

  @Override
  @AssertNonNullIfNonNull("sensorState")
  @Nullable
  public SensorStateWithDuration map2(@Nullable GenericRecord sensorState) {
    if (sensorState == null) {
      return null;
    }
    var event = Objects.requireNonNull(map((GenericRecord) sensorState.get(FIELD_EVENT)));
    return SensorStateWithDuration.builder()
        .setEvent(event)
        .setDuration((Duration) sensorState.get(FIELD_DURATION))
        .build();
  }

  @Override
  @AssertNonNullIfNonNull("sensorState")
  @Nullable
  public GenericRecord unmap2(@Nullable SensorStateWithDuration sensorState) {
    if (sensorState == null) {
      return null;
    }
    var event = Objects.requireNonNull(unmap(sensorState.getEvent()));
    return new GenericRecordBuilder(SensorStateWithDurationSchema.SCHEMA)
        .set(FIELD_EVENT, event)
        .set(FIELD_DURATION, sensorState.getDuration())
        .build();
  }
}
