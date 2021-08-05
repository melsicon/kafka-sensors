package de.melsicon.kafka.serde.ion;

import com.amazon.ion.IonWriter;
import java.io.IOException;

@FunctionalInterface
/* package */ interface IonSerialWriter<T> {
  /**
   * Writes one value.
   *
   * @param writer the {@link IonWriter}
   * @param message the message to serialize
   */
  void serialize(IonWriter writer, T message) throws IOException;
}
