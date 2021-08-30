package de.melsicon.kafka.sensors.serialization.confluentmapper;

import static de.melsicon.kafka.sensors.type.avro.generic.SchemaHelper.FIELD_DURATION;
import static de.melsicon.kafka.sensors.type.avro.generic.SchemaHelper.FIELD_EVENT;
import static de.melsicon.kafka.sensors.type.avro.generic.SchemaHelper.FIELD_ID;
import static de.melsicon.kafka.sensors.type.avro.generic.SchemaHelper.FIELD_STATE;
import static de.melsicon.kafka.sensors.type.avro.generic.SchemaHelper.FIELD_TIME;
import static de.melsicon.kafka.sensors.type.avro.generic.SchemaHelper.SENSOR_STATE_SCHEMA;
import static de.melsicon.kafka.sensors.type.avro.generic.SchemaHelper.SENSOR_STATE_WITH_DURATION_SCHEMA;

import com.google.errorprone.annotations.Immutable;
import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serialization.avromapper.GenericMapperHelper;
import de.melsicon.kafka.sensors.type.avro.logicaltypes.DurationMicroHelper;
import de.melsicon.kafka.sensors.type.avro.logicaltypes.InstantMicroHelper;
import java.util.Objects;
import javax.inject.Inject;
import org.apache.avro.generic.GenericEnumSymbol;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.checkerframework.checker.nullness.qual.PolyNull;

@Immutable
/* package */ final class ConfluentGenericMapper
    implements SensorStateMapper<GenericRecord, GenericRecord> {
  @Inject
  /* package */ ConfluentGenericMapper() {}

  @Override
  public @PolyNull SensorState map(@PolyNull GenericRecord sensorState) {
    if (sensorState == null) {
      return null;
    }
    return SensorState.builder()
        .id((String) sensorState.get(FIELD_ID))
        .time(InstantMicroHelper.fromLong((Long) sensorState.get(FIELD_TIME)))
        .state(GenericMapperHelper.stateMap((GenericEnumSymbol<?>) sensorState.get(FIELD_STATE)))
        .build();
  }

  @Override
  public @PolyNull GenericRecord unmap(@PolyNull SensorState sensorState) {
    if (sensorState == null) {
      return null;
    }
    return new GenericRecordBuilder(SENSOR_STATE_SCHEMA)
        .set(FIELD_ID, sensorState.getId())
        .set(FIELD_TIME, InstantMicroHelper.toLong(sensorState.getTime()))
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
        .duration(DurationMicroHelper.fromLong((Long) sensorState.get(FIELD_DURATION)))
        .build();
  }

  @Override
  public @PolyNull GenericRecord unmap2(@PolyNull SensorStateWithDuration sensorState) {
    if (sensorState == null) {
      return null;
    }
    var event = unmap(sensorState.getEvent());
    assert event != null : "@AssumeAssertion(nullness): error in unmap";
    return new GenericRecordBuilder(SENSOR_STATE_WITH_DURATION_SCHEMA)
        .set(FIELD_EVENT, event)
        .set(FIELD_DURATION, DurationMicroHelper.toLong(sensorState.getDuration()))
        .build();
  }
}
