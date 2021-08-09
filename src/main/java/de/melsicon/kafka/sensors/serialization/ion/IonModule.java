package de.melsicon.kafka.sensors.serialization.ion;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import de.melsicon.kafka.sensors.serde.Name;
import de.melsicon.kafka.sensors.serde.SensorStateSerdes;

@Module
public abstract class IonModule {
  private IonModule() {}

  @Binds
  @IntoMap
  @StringKey(Name.ION_BINARY)
  public abstract SensorStateSerdes ionBinarySerdes(IonBinarySerdes serdes);

  @Binds
  @IntoMap
  @StringKey(Name.ION_TEXT)
  public abstract SensorStateSerdes ionTextSerdes(IonTextSerdes serdes);
}
