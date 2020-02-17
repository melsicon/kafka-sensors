package de.melsicon.kafka.serde.avro;

import static de.melsicon.kafka.serde.avro.SchemaHelper.RESOLVER;
import static de.melsicon.kafka.serde.avro.SchemaHelper.RESOLVER_WITH_DURATION;

import de.melsicon.kafka.sensors.avro.SensorState;
import de.melsicon.kafka.sensors.avro.SensorStateWithDuration;
import de.melsicon.kafka.serde.Format;
import de.melsicon.kafka.serde.SensorStateSerdes;
import de.melsicon.kafka.serde.avromapper.AvroMapper;
import javax.inject.Inject;
import org.apache.avro.specific.SpecificData;
import org.apache.kafka.common.serialization.Serde;

public final class AvroSerdes implements SensorStateSerdes {
  private final AvroMapper<SensorState, SensorStateWithDuration> mapper;

  @Inject
  public AvroSerdes(AvroMapper<SensorState, SensorStateWithDuration> mapper) {
    this.mapper = mapper;
  }

  @Override
  public String name() {
    return "avro";
  }

  @Override
  public Format format() {
    return Format.AVRO;
  }

  @Override
  public Serde<de.melsicon.kafka.model.SensorState> createSensorStateSerde() {
    var model = SpecificData.getForClass(SensorState.class);
    model.setFastReaderEnabled(true);
    var schema = SensorState.getClassSchema();

    return SerdeHelper.createSerde(model, schema, mapper::unmap, mapper::map, RESOLVER);
  }

  @Override
  public Serde<de.melsicon.kafka.model.SensorStateWithDuration>
      createSensorStateWithDurationSerde() {
    var model = SpecificData.getForClass(SensorStateWithDuration.class);
    model.setFastReaderEnabled(true);
    var schema = SensorStateWithDuration.getClassSchema();

    return SerdeHelper.createSerde(
        model, schema, mapper::unmap2, mapper::map2, RESOLVER_WITH_DURATION);
  }
}
