package de.melsicon.kafka.serde.reflect;

import java.time.Instant;
import org.apache.avro.reflect.AvroDoc;
import org.apache.avro.reflect.AvroEncode;

@SuppressWarnings("NullAway")
@AvroDoc("State change of a sensor")
public final class SensorState {
  public String id;

  @AvroEncode(using = InstantAsLongEncoding.class)
  public Instant time;

  public State state;

  @AvroDoc("New state of the sensor")
  public enum State {
    OFF,
    ON
  }
}
