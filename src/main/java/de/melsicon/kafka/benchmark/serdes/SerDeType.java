package de.melsicon.kafka.benchmark.serdes;

import java.util.Locale;

public enum SerDeType {
  JSON,
  PROTO,
  AVRO,
  AVRO_DIRECT,
  AVRO_REFLECT,
  AVRO_GENERIC,
  CONFLUENT_SPECIFIC,
  CONFLUENT_DIRECT,
  CONFLUENT_REFLECT,
  CONFLUENT_GENERIC,
  CONFLUENT_JSON,
  CONFLUENT_PROTO,
  ION_TEXT,
  ION_BINARY;

  @Override
  public String toString() {
    return name().toLowerCase(Locale.ROOT).replace('_', '-');
  }
}
