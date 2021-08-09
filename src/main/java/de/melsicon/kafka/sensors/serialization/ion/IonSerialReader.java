package de.melsicon.kafka.sensors.serialization.ion;

import com.amazon.ion.IonReader;

@FunctionalInterface
/* package */ interface IonSerialReader<T> {
  /**
   * Reads one value.
   *
   * @param reader the {@link IonReader}
   */
  T deserialize(IonReader reader);
}
