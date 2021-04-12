package de.melsicon.kafka.context;

import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.ServiceManager;
import dagger.Module;
import dagger.Provides;
import java.util.Set;
import javax.inject.Singleton;

/** Bindings for our {@link Service}s. */
@Module
/* package */ abstract class StartUpModule {
  private StartUpModule() {}

  @Provides
  @Singleton
  /* package */ static ServiceManager serviceManager(Set<Service> services) {
    return new ServiceManager(services);
  }
}
