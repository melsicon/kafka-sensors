package de.melsicon.kafka.sensors.type.confluent.reflect;

import de.melsicon.kafka.sensors.type.avro.logicaltypes.DurationMicroHelper;
import de.melsicon.kafka.sensors.type.avro.logicaltypes.DurationMicrosConversion;
import java.io.IOException;
import java.time.Duration;
import org.apache.avro.Schema;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.Encoder;
import org.apache.avro.reflect.CustomEncoding;

/**
 * This encoder/decoder writes a java.time.Duration object as a long to avro and reads a Duration
 * object from long. The long stores the number of microseconds represented by the Duration object.
 */
public final class DurationAsLongEncoding extends CustomEncoding<Duration> {
  public DurationAsLongEncoding() {
    super();
    super.schema =
        DurationMicrosConversion.durationMicros().addToSchema(Schema.create(Schema.Type.LONG));
  }

  @Override
  protected void write(Object datum, Encoder out) throws IOException {
    var value = DurationMicroHelper.duration2Micros((Duration) datum);
    out.writeLong(value);
  }

  @Override
  protected Duration read(Object reuse, Decoder in) throws IOException {
    var value = in.readLong();
    return DurationMicroHelper.micros2Duraion(value);
  }
}
