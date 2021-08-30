package de.melsicon.kafka.sensors.serialization.avromapper;

import static de.melsicon.kafka.sensors.type.avro.generic.SchemaHelper.FIELD_DURATION;
import static de.melsicon.kafka.sensors.type.avro.generic.SchemaHelper.FIELD_EVENT;
import static de.melsicon.kafka.sensors.type.avro.generic.SchemaHelper.FIELD_ID;
import static de.melsicon.kafka.sensors.type.avro.generic.SchemaHelper.FIELD_STATE;
import static de.melsicon.kafka.sensors.type.avro.generic.SchemaHelper.FIELD_TIME;

import com.google.errorprone.annotations.Immutable;
import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.type.avro.generic.SchemaHelper;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import javax.inject.Inject;
import org.apache.avro.generic.GenericEnumSymbol;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.checkerframework.checker.nullness.qual.PolyNull;

@Immutable
/* package */ final class GenericMapper implements SensorStateMapper<GenericRecord, GenericRecord> {
  @Inject
  /* package */ GenericMapper() {}

  @Override
  public @PolyNull SensorState map(@PolyNull GenericRecord sensorState) {
    if (sensorState == null) {
      return null;
    }
    return SensorState.builder()
        .id((String) sensorState.get(FIELD_ID))
        .time((Instant) sensorState.get(FIELD_TIME))
        .state(GenericMapperHelper.stateMap((GenericEnumSymbol<?>) sensorState.get(FIELD_STATE)))
        .build();
  }

  @Override
  public @PolyNull GenericRecord unmap(@PolyNull SensorState sensorState) {
    if (sensorState == null) {
      return null;
    }
    return new GenericRecordBuilder(SchemaHelper.SENSOR_STATE_SCHEMA)
        .set(FIELD_ID, sensorState.getId())
        .set(FIELD_TIME, sensorState.getTime())
        .set(FIELD_STATE, GenericMapperHelper.stateUnmap(sensorState.getState()))
        .build();
  }

  @Override
  public @PolyNull SensorStateWithDuration map2(@PolyNull GenericRecord sensorState) {
    if (sensorState == null) {
      return null;
    }
    var event = Objects.requireNonNull(map((GenericRecord) sensorState.get(FIELD_EVENT)));
    return SensorStateWithDuration.builder()
        .event(event)
        .duration((Duration) sensorState.get(FIELD_DURATION))
        .build();
  }

  @Override
  public @PolyNull GenericRecord unmap2(@PolyNull SensorStateWithDuration sensorState) {
    if (sensorState == null) {
      return null;
    }
    var event = Objects.requireNonNull(unmap(sensorState.getEvent()));
    return new GenericRecordBuilder(SchemaHelper.SENSOR_STATE_WITH_DURATION_SCHEMA)
        .set(FIELD_EVENT, event)
        .set(FIELD_DURATION, sensorState.getDuration())
        .build();
  }
}
