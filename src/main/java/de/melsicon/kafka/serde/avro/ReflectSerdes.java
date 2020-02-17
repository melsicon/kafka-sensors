package de.melsicon.kafka.serde.avro;

import static de.melsicon.kafka.serde.avro.SchemaHelper.RESOLVER;
import static de.melsicon.kafka.serde.avro.SchemaHelper.RESOLVER_WITH_DURATION;

import de.melsicon.kafka.sensors.reflect.SensorState;
import de.melsicon.kafka.sensors.reflect.SensorStateWithDuration;
import de.melsicon.kafka.serde.Format;
import de.melsicon.kafka.serde.SensorStateSerdes;
import de.melsicon.kafka.serde.avromapper.AvroMapper;
import javax.inject.Inject;
import org.apache.kafka.common.serialization.Serde;

public final class ReflectSerdes implements SensorStateSerdes {
  private final AvroMapper<SensorState, SensorStateWithDuration> mapper;

  @Inject
  public ReflectSerdes(AvroMapper<SensorState, SensorStateWithDuration> mapper) {
    this.mapper = mapper;
  }

  @Override
  public String name() {
    return "reflect";
  }

  @Override
  public Format format() {
    return Format.AVRO;
  }

  @Override
  public Serde<de.melsicon.kafka.model.SensorState> createSensorStateSerde() {
    return SerdeHelper.createSerde(
        SensorState.MODEL, SensorState.SCHEMA, mapper::unmap, mapper::map, RESOLVER);
  }

  @Override
  public Serde<de.melsicon.kafka.model.SensorStateWithDuration>
      createSensorStateWithDurationSerde() {
    return SerdeHelper.createSerde(
        SensorStateWithDuration.MODEL,
        SensorStateWithDuration.SCHEMA,
        mapper::unmap2,
        mapper::map2,
        RESOLVER_WITH_DURATION);
  }
}
