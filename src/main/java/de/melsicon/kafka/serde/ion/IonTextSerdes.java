package de.melsicon.kafka.serde.ion;

import com.amazon.ion.system.IonTextWriterBuilder;
import javax.inject.Inject;

public final class IonTextSerdes extends IonSerdes {
  @Inject
  public IonTextSerdes() {
    super(IonTextWriterBuilder.standard().immutable());
  }

  @Override
  public String name() {
    return "ion-text";
  }
}
