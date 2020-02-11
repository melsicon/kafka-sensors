package de.melsicon.kafka.sensors.reflect;

import java.time.Duration;
import java.util.Objects;
import org.apache.avro.reflect.AvroEncode;

@SuppressWarnings("NullAway")
public final class SensorStateWithDuration {
  public SensorState event;

  @AvroEncode(using = DurationAsLongEncoding.class)
  public Duration duration;

  @Override
  public int hashCode() {
    return Objects.hash(event, duration);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SensorStateWithDuration)) {
      return false;
    }
    var that = (SensorStateWithDuration) o;
    return Objects.equals(event, that.event) && Objects.equals(duration, that.duration);
  }
}
