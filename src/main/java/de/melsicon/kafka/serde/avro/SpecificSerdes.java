package de.melsicon.kafka.serde.avro;

import static de.melsicon.kafka.serde.avro.SchemaHelper.RESOLVER;
import static de.melsicon.kafka.serde.avro.SchemaHelper.RESOLVER_WITH_DURATION;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.Format;
import de.melsicon.kafka.serde.SensorStateMapper;
import de.melsicon.kafka.serde.SensorStateSerdes;
import javax.inject.Inject;
import org.apache.avro.specific.SpecificData;
import org.apache.kafka.common.serialization.Serde;

public final class SpecificSerdes implements SensorStateSerdes {
  private final SensorStateMapper<
          de.melsicon.kafka.sensors.avro.SensorState,
          de.melsicon.kafka.sensors.avro.SensorStateWithDuration>
      mapper;

  @Inject
  public SpecificSerdes(
      SensorStateMapper<
              de.melsicon.kafka.sensors.avro.SensorState,
              de.melsicon.kafka.sensors.avro.SensorStateWithDuration>
          mapper) {
    this.mapper = mapper;
  }

  @Override
  public String name() {
    return "specific";
  }

  @Override
  public Format format() {
    return Format.AVRO;
  }

  @Override
  public Serde<SensorState> createSensorStateSerde() {
    var model = SpecificData.getForClass(de.melsicon.kafka.sensors.avro.SensorState.class);
    model.setFastReaderEnabled(true);
    var schema = de.melsicon.kafka.sensors.avro.SensorState.getClassSchema();

    return SerdeHelper.createSerde(model, schema, mapper::unmap, mapper::map, RESOLVER);
  }

  @Override
  public Serde<SensorStateWithDuration> createSensorStateWithDurationSerde() {
    var model =
        SpecificData.getForClass(de.melsicon.kafka.sensors.avro.SensorStateWithDuration.class);
    model.setFastReaderEnabled(true);
    var schema = de.melsicon.kafka.sensors.avro.SensorStateWithDuration.getClassSchema();

    return SerdeHelper.createSerde(
        model, schema, mapper::unmap2, mapper::map2, RESOLVER_WITH_DURATION);
  }
}
