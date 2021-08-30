package de.melsicon.kafka.sensors.type.avro.specific;

import de.melsicon.kafka.sensors.avro.SensorState;
import de.melsicon.kafka.sensors.avro.SensorStateWithDuration;
import org.apache.avro.Schema;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.SchemaStore;
import org.apache.avro.specific.SpecificData;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class SchemaHelper {
  public static final SpecificData SENSOR_STATE_MODEL;
  public static final Schema SENSOR_STATE_SCHEMA;
  public static final SpecificData SENSOR_STATE_WITH_DURATION_MODEL;
  public static final Schema SENSOR_STATE_WITH_DURATION_SCHEMA;

  public static final BinaryMessageEncoder<SensorState> SENSOR_STATE_ENCODER;
  public static final BinaryMessageEncoder<SensorStateWithDuration>
      SENSOR_STATE_WITH_DURATION_ENCODER;

  static {
    SENSOR_STATE_MODEL = SpecificData.getForClass(SensorState.class);
    SENSOR_STATE_MODEL.setFastReaderEnabled(true);
    SENSOR_STATE_MODEL.setCustomCoders(true);
    SENSOR_STATE_SCHEMA = SensorState.getClassSchema();

    SENSOR_STATE_WITH_DURATION_MODEL = SpecificData.getForClass(SensorStateWithDuration.class);
    SENSOR_STATE_WITH_DURATION_MODEL.setFastReaderEnabled(true);
    SENSOR_STATE_WITH_DURATION_MODEL.setCustomCoders(true);
    SENSOR_STATE_WITH_DURATION_SCHEMA = SensorStateWithDuration.getClassSchema();

    SENSOR_STATE_ENCODER = SensorState.getEncoder();
    SENSOR_STATE_WITH_DURATION_ENCODER = SensorStateWithDuration.getEncoder();
  }

  private SchemaHelper() {}

  @SuppressWarnings("nullness:argument")
  public static BinaryMessageDecoder<SensorState> createSensorStateDecoder(
      @Nullable SchemaStore resolver) {
    return SensorState.createDecoder(resolver);
  }

  @SuppressWarnings("nullness:argument")
  public static BinaryMessageDecoder<SensorStateWithDuration> createSensorStateWithDurationDecoder(
      @Nullable SchemaStore resolver) {
    return SensorStateWithDuration.createDecoder(resolver);
  }
}
