package de.melsicon.kafka.serde.avromapper;

import com.google.errorprone.annotations.Immutable;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.mapping.MapStructConfig;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.mapstruct.Mapper;

@Immutable
@Mapper(config = MapStructConfig.class)
public abstract class ConfluentReflectMapper
    implements AvroMapper<
        de.melsicon.kafka.sensors.confluent_reflect.SensorState,
        de.melsicon.kafka.sensors.confluent_reflect.SensorStateWithDuration> {
  public static ConfluentReflectMapper instance() {
    return new ConfluentReflectMapperImpl();
  }

  @Nullable
  @Override
  public abstract SensorState map(
      @Nullable de.melsicon.kafka.sensors.confluent_reflect.SensorState sensorState);

  @Nullable
  @Override
  public abstract de.melsicon.kafka.sensors.confluent_reflect.SensorState unmap(
      @Nullable SensorState sensorState);

  @Nullable
  @Override
  public abstract SensorStateWithDuration map2(
      @Nullable de.melsicon.kafka.sensors.confluent_reflect.SensorStateWithDuration sensorState);

  @Nullable
  @Override
  public abstract de.melsicon.kafka.sensors.confluent_reflect.SensorStateWithDuration unmap2(
      @Nullable SensorStateWithDuration sensorState);
}
