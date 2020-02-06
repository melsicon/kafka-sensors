package de.melsicon.kafka.sensors.generic;

import static org.apache.avro.LogicalType.LOGICAL_TYPE_PROP;

import org.apache.avro.LogicalTypes;
import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
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
  public static final Schema SCHEMA;
  /* package */ static final String NAMESPACE = "de.melsicon.kafka.sensors.generic";

  static {
    var stateSchema =
        SchemaBuilder.enumeration("State")
            .namespace("de.melsicon.kafka.sensors.avro")
            .doc("New state of the sensor")
            .aliases("de.melsicon.kafka.sensors.reflect.State")
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
            .type()
            .longBuilder()
            .prop(LOGICAL_TYPE_PROP, LogicalTypes.timestampMillis().getName())
            .endLong()
            .noDefault()
            .name(FIELD_STATE)
            .type(stateSchema)
            .noDefault()
            .endRecord();
  }

  private SensorStateSchema() {}
}
