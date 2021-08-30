package de.melsicon.kafka.sensors.type.avro.generic;

import de.melsicon.kafka.sensors.type.avro.logicaltypes.DurationMicrosConversion;
import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.data.TimeConversions.TimestampMicrosConversion;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericData.EnumSymbol;
import org.apache.avro.generic.GenericData.StringType;
import org.apache.avro.generic.GenericEnumSymbol;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.SchemaStore;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class SchemaHelper {
  public static final String FIELD_ID = "id";
  public static final String FIELD_TIME = "time";
  public static final String FIELD_STATE = "state";
  public static final String STATE_OFF = "OFF";
  public static final String STATE_ON = "ON";
  public static final GenericEnumSymbol<?> ENUM_OFF;
  public static final GenericEnumSymbol<?> ENUM_ON;

  public static final String FIELD_EVENT = "event";
  public static final String FIELD_DURATION = "duration";

  public static final GenericData MODEL;
  public static final Schema SENSOR_STATE_SCHEMA;
  public static final Schema SENSOR_STATE_WITH_DURATION_SCHEMA;

  public static final BinaryMessageEncoder<GenericRecord> SENSOR_STATE_ENCODER;
  public static final BinaryMessageEncoder<GenericRecord> SENSOR_STATE_WITH_DURATION_ENCODER;

  /* package */ static final String NAMESPACE = "de.melsicon.kafka.sensors.type.avro.generic";

  private static final String[] ENUM_ALIASES = {
    "de.melsicon.kafka.sensors.type.avro.reflect.SensorState.State",
    "de.melsicon.kafka.sensors.type.confluent.reflect.SensorState.State"
  };

  static {
    var timestampConversion = new TimestampMicrosConversion();
    var durationConversion = new DurationMicrosConversion();

    MODEL = new GenericData();
    MODEL.addLogicalTypeConversion(timestampConversion);
    MODEL.addLogicalTypeConversion(durationConversion);
    MODEL.setFastReaderEnabled(true);

    var stateSchema =
        SchemaBuilder.enumeration("State")
            .namespace("de.melsicon.kafka.sensors.avro")
            .doc("New state of the sensor")
            .aliases(ENUM_ALIASES)
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

    SENSOR_STATE_SCHEMA =
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

    SENSOR_STATE_WITH_DURATION_SCHEMA =
        SchemaBuilder.record("SensorStateWithDuration")
            .namespace(NAMESPACE)
            .doc("Duration a sensor was in this state")
            .fields()
            .name(FIELD_EVENT)
            .type(SENSOR_STATE_SCHEMA)
            .noDefault()
            .name(FIELD_DURATION)
            .type(durationConversion.getRecommendedSchema())
            .noDefault()
            .endRecord();

    SENSOR_STATE_ENCODER = new BinaryMessageEncoder<>(MODEL, SENSOR_STATE_SCHEMA);
    SENSOR_STATE_WITH_DURATION_ENCODER =
        new BinaryMessageEncoder<>(MODEL, SENSOR_STATE_WITH_DURATION_SCHEMA);
  }

  private SchemaHelper() {}

  @SuppressWarnings("nullness:argument")
  public static BinaryMessageDecoder<GenericRecord> createSensorStateDecoder(
      @Nullable SchemaStore resolver) {
    return new BinaryMessageDecoder<>(MODEL, SENSOR_STATE_SCHEMA, resolver);
  }

  @SuppressWarnings("nullness:argument")
  public static BinaryMessageDecoder<GenericRecord> createSensorStateWithDurationDecoder(
      @Nullable SchemaStore resolver) {
    return new BinaryMessageDecoder<>(MODEL, SENSOR_STATE_WITH_DURATION_SCHEMA, resolver);
  }
}
