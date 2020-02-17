package de.melsicon.kafka.sensors.confluent_reflect;

import java.io.IOException;
import java.time.Instant;
import org.apache.avro.LogicalTypes;
import org.apache.avro.Schema;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.Encoder;
import org.apache.avro.reflect.CustomEncoding;

public final class InstantAsLongEncoding extends CustomEncoding<Instant> {
  public InstantAsLongEncoding() {
    super.schema = LogicalTypes.timestampMillis().addToSchema(Schema.create(Schema.Type.LONG));
  }

  @Override
  protected void write(Object datum, Encoder out) throws IOException {
    var value = ((Instant) datum).toEpochMilli();
    out.writeLong(value);
  }

  @Override
  protected Instant read(Object reuse, Decoder in) throws IOException {
    var value = in.readLong();
    return Instant.ofEpochMilli(value);
  }
}
