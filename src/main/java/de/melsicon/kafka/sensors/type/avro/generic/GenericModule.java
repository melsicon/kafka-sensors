package de.melsicon.kafka.sensors.type.avro.generic;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import org.apache.avro.Schema;

@Module
public abstract class GenericModule {
  private GenericModule() {}

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
