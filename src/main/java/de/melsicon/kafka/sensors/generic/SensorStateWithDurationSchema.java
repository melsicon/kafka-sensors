package de.melsicon.kafka.sensors.generic;

import static de.melsicon.kafka.sensors.generic.SensorStateSchema.NAMESPACE;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;

public final class SensorStateWithDurationSchema {
  public static final String FIELD_EVENT = "event";
  public static final String FIELD_DURATION = "duration";
  public static final Schema SCHEMA;

  static {
    SCHEMA =
        SchemaBuilder.record("SensorStateWithDuration")
            .namespace(NAMESPACE)
            .doc("Duration a sensor was in this state")
            .fields()
            .requiredLong(FIELD_DURATION)
            .name(FIELD_EVENT)
            .type(SensorStateSchema.SCHEMA)
            .noDefault()
            .endRecord();
  }

  private SensorStateWithDurationSchema() {}
}
