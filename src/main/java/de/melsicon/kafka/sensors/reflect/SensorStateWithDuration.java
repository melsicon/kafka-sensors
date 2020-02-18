package de.melsicon.kafka.sensors.reflect;

import de.melsicon.kafka.sensors.logicaltypes.DurationMillisConversion;
import java.time.Duration;
import java.util.Objects;
import org.apache.avro.Schema;
import org.apache.avro.data.TimeConversions.TimestampMillisConversion;
import org.apache.avro.reflect.AvroDoc;
import org.apache.avro.reflect.ReflectData;

@SuppressWarnings("NullAway")
@AvroDoc("Duration a sensor was in this state")
public final class SensorStateWithDuration {
  public static final ReflectData MODEL;
  public static final Schema SCHEMA;

  static {
    MODEL = new ReflectData();
    MODEL.addLogicalTypeConversion(new TimestampMillisConversion());
    MODEL.addLogicalTypeConversion(new DurationMillisConversion());

    SCHEMA = MODEL.getSchema(SensorStateWithDuration.class);
  }

  public SensorState event;

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
