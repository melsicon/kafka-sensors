package de.melsicon.kafka.sensors.serialization.avro;

import de.melsicon.kafka.sensors.serde.Format;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serde.SensorStateSerdes;
import de.melsicon.kafka.sensors.type.avro.reflect.SchemaHelper;
import de.melsicon.kafka.sensors.type.avro.reflect.SensorState;
import de.melsicon.kafka.sensors.type.avro.reflect.SensorStateWithDuration;
import javax.inject.Inject;
import org.apache.avro.message.SchemaStore;
import org.apache.kafka.common.serialization.Serde;

public final class ReflectSerdes implements SensorStateSerdes {
  private final SensorStateMapper<SensorState, SensorStateWithDuration> mapper;
  private final SchemaStore resolver;

  @Inject
  /* package */ ReflectSerdes(
      SensorStateMapper<SensorState, SensorStateWithDuration> mapper, SchemaStore resolver) {
    this.mapper = mapper;
    this.resolver = resolver;
  }

  @Override
  public Format format() {
    return Format.AVRO;
  }

  @Override
  public Serde<de.melsicon.kafka.sensors.model.SensorState> createSensorStateSerde() {
    var encoder = SchemaHelper.SENSOR_STATE_ENCODER;
    var decoder = SchemaHelper.createSensorStateDecoder(resolver);

    return SerdeHelper.createSerde(encoder, decoder, mapper::unmap, mapper::map, resolver);
  }

  @Override
  public Serde<de.melsicon.kafka.sensors.model.SensorStateWithDuration>
      createSensorStateWithDurationSerde() {
    var encoder = SchemaHelper.SENSOR_STATE_WITH_DURATION_ENCODER;
    var decoder = SchemaHelper.createSensorStateWithDurationDecoder(resolver);

    return SerdeHelper.createSerde(encoder, decoder, mapper::unmap2, mapper::map2, resolver);
  }
}
