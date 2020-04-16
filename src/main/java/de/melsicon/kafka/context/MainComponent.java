package de.melsicon.kafka.context;

import dagger.BindsInstance;
import dagger.Component;
import de.melsicon.kafka.lifecycle.StartUpManager;
import io.helidon.config.Config;
import javax.inject.Singleton;

@Component(modules = {MainModule.class})
@Singleton
public abstract class MainComponent {
  /* package */ MainComponent() {}

  public static Factory factory() {
    return DaggerMainComponent.factory();
  }

  public abstract StartUpManager startUpManager();

  @Component.Factory
  public abstract static class Factory {
    public abstract MainComponent newMainComponent(@BindsInstance Config config);
  }
}
