package de.melsicon.kafka.sensors.reflect;

import java.io.IOException;
import java.time.Duration;
import org.apache.avro.Schema;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.Encoder;
import org.apache.avro.reflect.CustomEncoding;

public final class DurationAsLongEncoding extends CustomEncoding<Duration> {
  public DurationAsLongEncoding() {
    super.schema = Schema.create(Schema.Type.LONG);
  }

  @Override
  protected void write(Object datum, Encoder out) throws IOException {
    var value = ((Duration) datum).toMillis();
    out.writeLong(value);
  }

  @Override
  protected Duration read(Object reuse, Decoder in) throws IOException {
    var value = in.readLong();
    return Duration.ofMillis(value);
  }
}
