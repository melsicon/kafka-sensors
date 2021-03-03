package de.melsicon.kafka.sensors.reflect;

import java.time.Instant;
import java.util.Objects;
import org.apache.avro.Schema;
import org.apache.avro.data.TimeConversions.TimestampMillisConversion;
import org.apache.avro.reflect.AvroDoc;
import org.apache.avro.reflect.ReflectData;
import org.checkerframework.checker.nullness.qual.Nullable;

@SuppressWarnings({"NullAway", "nullness:initialization.field.uninitialized"})
@AvroDoc("State change of a sensor")
public final class SensorState {
  public static final ReflectData MODEL;
  public static final Schema SCHEMA;

  static {
    MODEL = new ReflectData();
    MODEL.addLogicalTypeConversion(new TimestampMillisConversion());

    SCHEMA = MODEL.getSchema(SensorState.class);
  }

  public String id;

  public Instant time;

  public State state;

  @Override
  public int hashCode() {
    return Objects.hash(id, time, state);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SensorState)) {
      return false;
    }
    var that = (SensorState) o;
    return Objects.equals(id, that.id) && Objects.equals(time, that.time) && state == that.state;
  }
}
