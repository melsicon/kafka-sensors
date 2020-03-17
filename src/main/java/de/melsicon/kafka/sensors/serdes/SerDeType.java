package de.melsicon.kafka.sensors.serdes;

import java.util.Locale;

public enum SerDeType {
  JSON,
  PROTO,
  AVRO,
  AVRO_REFLECT,
  AVRO_GENERIC,
  CONFLUENT,
  CONFLUENT_REFLECT,
  CONFLUENT_GENERIC;

  @Override
  public String toString() {
    return name().toLowerCase(Locale.ROOT).replace('_', '-');
  }
}
