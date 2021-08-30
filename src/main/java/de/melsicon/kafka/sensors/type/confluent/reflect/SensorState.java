package de.melsicon.kafka.sensors.type.confluent.reflect;

import com.google.common.base.MoreObjects;
import java.time.Instant;
import java.util.Objects;
import org.apache.avro.reflect.AvroAlias;
import org.apache.avro.reflect.AvroDoc;
import org.apache.avro.reflect.AvroEncode;
import org.checkerframework.checker.nullness.qual.Nullable;

@SuppressWarnings("nullness:initialization.field.uninitialized")
@AvroDoc("State change of a sensor")
public final class SensorState {
  public String id;

  @AvroEncode(using = InstantAsLongEncoding.class)
  public Instant time;

  public State state;

  @Override
  public int hashCode() {
    return Objects.hash(id, time, state);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    return this == o
        || (o instanceof SensorState that
            && Objects.equals(id, that.id)
            && Objects.equals(time, that.time)
            && state == that.state);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .add("time", time)
        .add("state", state)
        .toString();
  }

  @AvroDoc("New state of the sensor")
  @AvroAlias(alias = "State", space = "de.melsicon.kafka.sensors.avro")
  public enum State {
    OFF,
    ON
  }
}
