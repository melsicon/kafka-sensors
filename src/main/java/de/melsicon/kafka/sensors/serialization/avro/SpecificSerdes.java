package de.melsicon.kafka.sensors.serialization.avro;

import static de.melsicon.kafka.sensors.serialization.avro.SchemaHelper.RESOLVER;
import static de.melsicon.kafka.sensors.serialization.avro.SchemaHelper.RESOLVER_WITH_DURATION;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.serde.Format;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serde.SensorStateSerdes;
import org.apache.avro.specific.SpecificData;
import org.apache.kafka.common.serialization.Serde;

public final class SpecificSerdes implements SensorStateSerdes {
  private final SensorStateMapper<
          de.melsicon.kafka.sensors.avro.SensorState,
          de.melsicon.kafka.sensors.avro.SensorStateWithDuration>
      mapper;

  @AssistedInject
  public SpecificSerdes(
      @Assisted
          SensorStateMapper<
                  de.melsicon.kafka.sensors.avro.SensorState,
                  de.melsicon.kafka.sensors.avro.SensorStateWithDuration>
              mapper) {
    this.mapper = mapper;
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
