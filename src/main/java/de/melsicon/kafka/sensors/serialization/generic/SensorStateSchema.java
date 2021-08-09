package de.melsicon.kafka.sensors.serialization.generic;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.data.TimeConversions.TimestampMillisConversion;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericData.EnumSymbol;
import org.apache.avro.generic.GenericData.StringType;
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
  /* package */ static final String NAMESPACE = SensorStateSchema.class.getPackageName();

  static {
    var timestampConversion = new TimestampMillisConversion();

    MODEL = new GenericData();
    MODEL.addLogicalTypeConversion(timestampConversion);

    var stateSchema =
        SchemaBuilder.enumeration("State")
            .namespace("de.melsicon.kafka.sensors.avro")
            .doc("New state of the sensor")
            .aliases(
                de.melsicon.kafka.sensors.serialization.reflect.SensorState.State.class
                    .getCanonicalName(),
                de.melsicon.kafka.sensors.serialization.confluent_reflect.SensorState.State.class
                    .getCanonicalName())
            .symbols(STATE_OFF, STATE_ON);

    ENUM_OFF = new EnumSymbol(stateSchema, STATE_OFF);
    ENUM_ON = new EnumSymbol(stateSchema, STATE_ON);

    /* Reusable shortcut `.type(stringSchema)` for
     *   .type()
     *     .stringBuilder()
     *     .prop("avro.java.string", "String")
     *     .endString()
     */
    var stringSchema = Schema.create(Schema.Type.STRING);
    stringSchema.addProp(GenericData.STRING_PROP, StringType.String.name());

    var timestampSchema = timestampConversion.getRecommendedSchema();

    SCHEMA =
        SchemaBuilder.record("SensorState")
            .namespace(NAMESPACE)
            .doc("State change of a sensor")
            .fields()
            .name(FIELD_ID)
            .type(stringSchema)
            .noDefault()
            .name(FIELD_TIME)
            .type(timestampSchema)
            .noDefault()
            .name(FIELD_STATE)
            .type(stateSchema)
            .noDefault()
            .endRecord();
  }

  private SensorStateSchema() {}
}
