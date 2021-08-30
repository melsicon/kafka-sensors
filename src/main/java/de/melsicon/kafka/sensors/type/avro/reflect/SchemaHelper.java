package de.melsicon.kafka.sensors.type.avro.reflect;

import de.melsicon.kafka.sensors.type.avro.logicaltypes.DurationMicrosConversion;
import org.apache.avro.Schema;
import org.apache.avro.data.TimeConversions.TimestampMicrosConversion;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.SchemaStore;
import org.apache.avro.reflect.ReflectData;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class SchemaHelper {
  public static final ReflectData MODEL;
  public static final Schema SENSOR_STATE_SCHEMA;
  public static final Schema SENSOR_STATE_WITH_DURATION_SCHEMA;

  public static final BinaryMessageEncoder<SensorState> SENSOR_STATE_ENCODER;
  public static final BinaryMessageEncoder<SensorStateWithDuration>
      SENSOR_STATE_WITH_DURATION_ENCODER;

  static {
    MODEL = new ReflectData();
    MODEL.addLogicalTypeConversion(new TimestampMicrosConversion());
    MODEL.addLogicalTypeConversion(new DurationMicrosConversion());
    MODEL.setFastReaderEnabled(true);

    SENSOR_STATE_SCHEMA = MODEL.getSchema(SensorState.class);
    SENSOR_STATE_WITH_DURATION_SCHEMA = MODEL.getSchema(SensorStateWithDuration.class);

    SENSOR_STATE_ENCODER = new BinaryMessageEncoder<>(MODEL, SENSOR_STATE_SCHEMA);
    SENSOR_STATE_WITH_DURATION_ENCODER =
        new BinaryMessageEncoder<>(MODEL, SENSOR_STATE_WITH_DURATION_SCHEMA);
  }

  private SchemaHelper() {}

  @SuppressWarnings("nullness:argument")
  public static BinaryMessageDecoder<SensorState> createSensorStateDecoder(
      @Nullable SchemaStore resolver) {
    return new BinaryMessageDecoder<>(MODEL, SENSOR_STATE_SCHEMA, resolver);
  }

  @SuppressWarnings("nullness:argument")
  public static BinaryMessageDecoder<SensorStateWithDuration> createSensorStateWithDurationDecoder(
      @Nullable SchemaStore resolver) {
    return new BinaryMessageDecoder<>(MODEL, SENSOR_STATE_WITH_DURATION_SCHEMA, resolver);
  }
}
