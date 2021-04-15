package de.melsicon.kafka.serde.ion;

import com.amazon.ion.system.IonBinaryWriterBuilder;
import de.melsicon.kafka.serde.Format;
import javax.inject.Inject;

public final class IonBinarySerdes extends IonSerdes {
  @Inject
  public IonBinarySerdes() {
    super(IonBinaryWriterBuilder.standard().immutable());
  }

  @Override
  public String name() {
    return "ion-binary";
  }

  @Override
  public Format format() {
    return Format.ION;
  }
}
