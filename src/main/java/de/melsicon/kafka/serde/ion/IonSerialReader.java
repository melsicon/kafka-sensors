package de.melsicon.kafka.serde.ion;

import com.amazon.ion.IonReader;

@FunctionalInterface
public interface IonSerialReader<T> {

  /**
   * Reads one value.
   *
   * @param reader the {@link IonReader}
   */
  T deserialize(IonReader reader);
}
