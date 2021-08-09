package de.melsicon.kafka.sensors.serialization.avro;

import static de.melsicon.kafka.sensors.serialization.avro.SchemaHelper.RESOLVER;
import static de.melsicon.kafka.sensors.serialization.avro.SchemaHelper.RESOLVER_WITH_DURATION;

import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.serde.Format;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serde.SensorStateSerdes;
import javax.inject.Inject;
import org.apache.kafka.common.serialization.Serde;

public final class ReflectSerdes implements SensorStateSerdes {
  private final SensorStateMapper<
          de.melsicon.kafka.sensors.serialization.reflect.SensorState,
          de.melsicon.kafka.sensors.serialization.reflect.SensorStateWithDuration>
      mapper;

  @Inject
  /* package */ ReflectSerdes(
      SensorStateMapper<
              de.melsicon.kafka.sensors.serialization.reflect.SensorState,
              de.melsicon.kafka.sensors.serialization.reflect.SensorStateWithDuration>
          mapper) {
    this.mapper = mapper;
  }

  @Override
  public Format format() {
    return Format.AVRO;
  }

  @Override
  public Serde<SensorState> createSensorStateSerde() {
    return SerdeHelper.createSerde(
        de.melsicon.kafka.sensors.serialization.reflect.SensorState.MODEL,
        de.melsicon.kafka.sensors.serialization.reflect.SensorState.SCHEMA,
        mapper::unmap,
        mapper::map,
        RESOLVER);
  }

  @Override
  public Serde<SensorStateWithDuration> createSensorStateWithDurationSerde() {
    return SerdeHelper.createSerde(
        de.melsicon.kafka.sensors.serialization.reflect.SensorStateWithDuration.MODEL,
        de.melsicon.kafka.sensors.serialization.reflect.SensorStateWithDuration.SCHEMA,
        mapper::unmap2,
        mapper::map2,
        RESOLVER_WITH_DURATION);
  }
}
