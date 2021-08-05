package de.melsicon.kafka.serde.ion;

import static de.melsicon.kafka.serde.Name.*;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import de.melsicon.kafka.serde.SensorStateSerdes;

@Module
public abstract class IonModule {
  private IonModule() {}

  @Binds
  @IntoMap
  @StringKey(ION_BINARY)
  public abstract SensorStateSerdes ionBinarySerdes(IonBinarySerdes serdes);

  @Binds
  @IntoMap
  @StringKey(ION_TEXT)
  public abstract SensorStateSerdes ionTextSerdes(IonTextSerdes serdes);
}
