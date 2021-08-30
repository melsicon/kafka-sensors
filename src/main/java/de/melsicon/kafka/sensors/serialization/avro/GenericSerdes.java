package de.melsicon.kafka.sensors.serialization.avro;

import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.serde.Format;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serde.SensorStateSerdes;
import de.melsicon.kafka.sensors.type.avro.generic.SchemaHelper;
import javax.inject.Inject;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.message.SchemaStore;
import org.apache.kafka.common.serialization.Serde;

public final class GenericSerdes implements SensorStateSerdes {
  private final SensorStateMapper<GenericRecord, GenericRecord> mapper;
  private final SchemaStore resolver;

  @Inject
  /* package */ GenericSerdes(
      SensorStateMapper<GenericRecord, GenericRecord> mapper, SchemaStore resolver) {
    this.mapper = mapper;
    this.resolver = resolver;
  }

  @Override
  public Format format() {
    return Format.AVRO;
  }

  @Override
  public Serde<SensorState> createSensorStateSerde() {
    var encoder = SchemaHelper.SENSOR_STATE_ENCODER;
    var decoder = SchemaHelper.createSensorStateDecoder(resolver);

    return SerdeHelper.createSerde(encoder, decoder, mapper::unmap, mapper::map, resolver);
  }

  @Override
  public Serde<SensorStateWithDuration> createSensorStateWithDurationSerde() {
    var encoder = SchemaHelper.SENSOR_STATE_WITH_DURATION_ENCODER;
    var decoder = SchemaHelper.createSensorStateWithDurationDecoder(resolver);

    return SerdeHelper.createSerde(encoder, decoder, mapper::unmap2, mapper::map2, resolver);
  }
}
