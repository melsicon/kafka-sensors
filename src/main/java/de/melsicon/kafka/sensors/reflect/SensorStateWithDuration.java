package de.melsicon.kafka.sensors.reflect;

import java.time.Duration;
import org.apache.avro.reflect.AvroEncode;

@SuppressWarnings("NullAway")
public final class SensorStateWithDuration {
  public SensorState event;

  @AvroEncode(using = DurationAsLongEncoding.class)
  public Duration duration;
}