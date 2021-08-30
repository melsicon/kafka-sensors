package de.melsicon.kafka.sensors.type.avro.reflect;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import org.apache.avro.Schema;

@Module
public abstract class ReflectModule {
  private ReflectModule() {}

  @Provides
  @IntoSet
  /* package */ static Schema sensorStateSchema() {
    return SchemaHelper.SENSOR_STATE_SCHEMA;
  }

  @Provides
  @IntoSet
  /* package */ static Schema sensorStateWithDurationSchema() {
    return SchemaHelper.SENSOR_STATE_WITH_DURATION_SCHEMA;
  }
}
