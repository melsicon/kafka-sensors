package de.melsicon.kafka.sensors.serialization.generic;

import de.melsicon.kafka.sensors.serialization.logicaltypes.DurationMicrosConversion;
import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.data.TimeConversions.TimestampMicrosConversion;
import org.apache.avro.generic.GenericData;

public final class SensorStateWithDurationSchema {
  public static final String FIELD_EVENT = "event";
  public static final String FIELD_DURATION = "duration";
  public static final GenericData MODEL;
  public static final Schema SCHEMA;

  static {
    var timestampConversion = new TimestampMicrosConversion();
    var durationConversion = new DurationMicrosConversion();

    MODEL = new GenericData();
    MODEL.addLogicalTypeConversion(timestampConversion);
    MODEL.addLogicalTypeConversion(durationConversion);

    SCHEMA =
        SchemaBuilder.record("SensorStateWithDuration")
            .namespace(SensorStateSchema.NAMESPACE)
            .doc("Duration a sensor was in this state")
            .fields()
            .name(FIELD_EVENT)
            .type(SensorStateSchema.SCHEMA)
            .noDefault()
            .name(FIELD_DURATION)
            .type(durationConversion.getRecommendedSchema())
            .noDefault()
            .endRecord();
  }

  private SensorStateWithDurationSchema() {}
}
