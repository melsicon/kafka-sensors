package de.melsicon.kafka.serde.avromapper;

import com.google.errorprone.annotations.Immutable;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.mapping.MapStructConfig;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.mapstruct.Mapper;

@Immutable
@Mapper(config = MapStructConfig.class)
public abstract class ReflectMapper
    implements AvroMapper<
        de.melsicon.kafka.sensors.reflect.SensorState,
        de.melsicon.kafka.sensors.reflect.SensorStateWithDuration> {
  public static ReflectMapper instance() {
    return new ReflectMapperImpl();
  }

  @Nullable
  @Override
  public abstract SensorState map(
      @Nullable de.melsicon.kafka.sensors.reflect.SensorState sensorState);

  @Nullable
  @Override
  public abstract de.melsicon.kafka.sensors.reflect.SensorState unmap(
      @Nullable SensorState sensorState);

  @Nullable
  @Override
  public abstract SensorStateWithDuration map2(
      @Nullable de.melsicon.kafka.sensors.reflect.SensorStateWithDuration sensorState);

  @Nullable
  @Override
  public abstract de.melsicon.kafka.sensors.reflect.SensorStateWithDuration unmap2(
      @Nullable SensorStateWithDuration sensorState);
}
