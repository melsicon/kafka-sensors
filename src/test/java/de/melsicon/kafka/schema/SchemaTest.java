package de.melsicon.kafka.schema;

import com.google.common.collect.ImmutableCollection;
import org.apache.avro.Schema;
import org.apache.avro.SchemaValidationException;
import org.apache.avro.SchemaValidatorBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public final class SchemaTest {
  private final Schema schema;
  private final Iterable<Schema> schemas;

  public SchemaTest(Schema schema, Iterable<Schema> schemas) {
    this.schema = schema;
    this.schemas = schemas;
  }

  @Parameters()
  public static ImmutableCollection<?> parameters() {
    return TestHelper.createParameters();
  }

  @Test
  public void canReadAll() throws SchemaValidationException {
    new SchemaValidatorBuilder().canReadStrategy().validateAll().validate(schema, schemas);
  }
}
