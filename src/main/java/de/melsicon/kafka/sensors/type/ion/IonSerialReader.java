package de.melsicon.kafka.sensors.type.ion;

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
