package de.melsicon.kafka.sensors.serdes;

import java.util.Locale;

public enum SerDeType {
  JSON,
  PROTO,
  AVRO,
  AVRO_REFLECT,
  AVRO_GENERIC,
  CONFLUENT_SPECIFIC,
  CONFLUENT_REFLECT,
  CONFLUENT_GENERIC,
  CONFLUENT_JSON,
  CONFLUENT_PROTO;

  @Override
  public String toString() {
    return name().toLowerCase(Locale.ROOT).replace('_', '-');
  }
}
