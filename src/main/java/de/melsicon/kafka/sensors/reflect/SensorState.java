package de.melsicon.kafka.sensors.reflect;

import java.time.Instant;
import java.util.Objects;
import org.apache.avro.reflect.AvroDoc;
import org.apache.avro.reflect.AvroEncode;

@SuppressWarnings("NullAway")
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
  public boolean equals(Object o) {
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
