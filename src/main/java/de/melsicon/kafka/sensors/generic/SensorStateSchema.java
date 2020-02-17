package de.melsicon.kafka.sensors.generic;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.data.TimeConversions.TimestampMillisConversion;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericData.EnumSymbol;
import org.apache.avro.generic.GenericEnumSymbol;

public final class SensorStateSchema {
  public static final String FIELD_ID = "id";
  public static final String FIELD_TIME = "time";
  public static final String FIELD_STATE = "state";
  public static final String STATE_OFF = "OFF";
  public static final String STATE_ON = "ON";
  public static final GenericEnumSymbol<?> ENUM_OFF;
  public static final GenericEnumSymbol<?> ENUM_ON;
  public static final GenericData MODEL;
  public static final Schema SCHEMA;
  /* package */ static final String NAMESPACE = "de.melsicon.kafka.sensors.generic";

  static {
    var timestampConversion = new TimestampMillisConversion();

    MODEL = new GenericData();
    MODEL.addLogicalTypeConversion(timestampConversion);

    var stateSchema =
        SchemaBuilder.enumeration("State")
            .namespace("de.melsicon.kafka.sensors.avro")
            .doc("New state of the sensor")
            .aliases(
                "de.melsicon.kafka.sensors.reflect.State",
                "de.melsicon.kafka.sensors.confluent_reflect.State")
            .symbols(STATE_OFF, STATE_ON);

    ENUM_OFF = new EnumSymbol(stateSchema, STATE_OFF);
    ENUM_ON = new EnumSymbol(stateSchema, STATE_ON);

    SCHEMA =
        SchemaBuilder.record("SensorState")
            .namespace(NAMESPACE)
            .doc("State change of a sensor")
            .fields()
            .requiredString(FIELD_ID)
            .name(FIELD_TIME)
            .type(timestampConversion.getRecommendedSchema())
            .noDefault()
            .name(FIELD_STATE)
            .type(stateSchema)
            .noDefault()
            .endRecord();
  }

  private SensorStateSchema() {}
}
