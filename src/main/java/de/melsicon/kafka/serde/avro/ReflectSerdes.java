package de.melsicon.kafka.serde.avro;

import static de.melsicon.kafka.serde.avro.SchemaHelper.RESOLVER;
import static de.melsicon.kafka.serde.avro.SchemaHelper.RESOLVER_WITH_DURATION;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.Format;
import de.melsicon.kafka.serde.SensorStateMapper;
import de.melsicon.kafka.serde.SensorStateSerdes;
import javax.inject.Inject;
import org.apache.kafka.common.serialization.Serde;

public final class ReflectSerdes implements SensorStateSerdes {
  private final SensorStateMapper<
          de.melsicon.kafka.sensors.reflect.SensorState,
          de.melsicon.kafka.sensors.reflect.SensorStateWithDuration>
      mapper;

  @Inject
  /* package */ ReflectSerdes(
      SensorStateMapper<
              de.melsicon.kafka.sensors.reflect.SensorState,
              de.melsicon.kafka.sensors.reflect.SensorStateWithDuration>
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
        de.melsicon.kafka.sensors.reflect.SensorState.MODEL,
        de.melsicon.kafka.sensors.reflect.SensorState.SCHEMA,
        mapper::unmap,
        mapper::map,
        RESOLVER);
  }

  @Override
  public Serde<SensorStateWithDuration> createSensorStateWithDurationSerde() {
    return SerdeHelper.createSerde(
        de.melsicon.kafka.sensors.reflect.SensorStateWithDuration.MODEL,
        de.melsicon.kafka.sensors.reflect.SensorStateWithDuration.SCHEMA,
        mapper::unmap2,
        mapper::map2,
        RESOLVER_WITH_DURATION);
  }
}
