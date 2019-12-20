package de.melsicon.kafka.serde.reflect;

import java.io.IOException;
import java.time.Instant;
import org.apache.avro.LogicalTypes;
import org.apache.avro.Schema;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.Encoder;
import org.apache.avro.reflect.CustomEncoding;

public final class InstantAsLongEncoding extends CustomEncoding<Instant> {
  public InstantAsLongEncoding() {
    var schema = Schema.create(Schema.Type.LONG);
    var logicalType = LogicalTypes.timestampMillis();
    super.schema = logicalType.addToSchema(schema);
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
