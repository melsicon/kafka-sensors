package de.melsicon.kafka.serde.avro;

import static de.melsicon.kafka.serde.avro.SchemaHelper.RESOLVER;
import static de.melsicon.kafka.serde.avro.SchemaHelper.RESOLVER_WITH_DURATION;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.generic.SensorStateSchema;
import de.melsicon.kafka.sensors.generic.SensorStateWithDurationSchema;
import de.melsicon.kafka.serde.Format;
import de.melsicon.kafka.serde.SensorStateMapper;
import de.melsicon.kafka.serde.SensorStateSerdes;
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

    return SerdeHelper.createSerde(model, schema, mapper::unmap, mapper::map, RESOLVER);
  }

  @Override
  public Serde<SensorStateWithDuration> createSensorStateWithDurationSerde() {
    var model = SensorStateWithDurationSchema.MODEL;
    model.setFastReaderEnabled(true);
    var schema = SensorStateWithDurationSchema.SCHEMA;

    return SerdeHelper.createSerde(
        model, schema, mapper::unmap2, mapper::map2, RESOLVER_WITH_DURATION);
  }
}
