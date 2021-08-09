package de.melsicon.kafka.sensors.serialization.avro;

import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.serde.Format;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serde.SensorStateSerdes;
import de.melsicon.kafka.sensors.serialization.generic.SensorStateSchema;
import de.melsicon.kafka.sensors.serialization.generic.SensorStateWithDurationSchema;
import javax.inject.Inject;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.serialization.Serde;

public final class GenericSerdes implements SensorStateSerdes {
  private final SensorStateMapper<GenericRecord, GenericRecord> mapper;

  @Inject
  /* package */ GenericSerdes(SensorStateMapper<GenericRecord, GenericRecord> mapper) {
    this.mapper = mapper;
  }

  @Override
  public Format format() {
    return Format.AVRO;
  }

  @Override
  public Serde<SensorState> createSensorStateSerde() {
    var model = SensorStateSchema.MODEL;
    model.setFastReaderEnabled(true);
    var schema = SensorStateSchema.SCHEMA;

    return SerdeHelper.createSerde(
        model, schema, mapper::unmap, mapper::map, SchemaHelper.RESOLVER);
  }

  @Override
  public Serde<SensorStateWithDuration> createSensorStateWithDurationSerde() {
    var model = SensorStateWithDurationSchema.MODEL;
    model.setFastReaderEnabled(true);
    var schema = SensorStateWithDurationSchema.SCHEMA;

    return SerdeHelper.createSerde(
        model, schema, mapper::unmap2, mapper::map2, SchemaHelper.RESOLVER_WITH_DURATION);
  }
}
