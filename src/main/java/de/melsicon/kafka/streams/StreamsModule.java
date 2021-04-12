package de.melsicon.kafka.streams;

import com.google.common.util.concurrent.Service;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoSet;

/** Binds {@link MyStreams} to a {@link Service}. */
@Module
public abstract class StreamsModule {
  private StreamsModule() {}

  @Binds
  @IntoSet
  /* package */ abstract Service myStreams(MyStreams myStreams);
}
