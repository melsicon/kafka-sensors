package de.melsicon.kafka.sensors.serialization.logicaltypes;

import java.time.Duration;
import org.apache.avro.Conversion;
import org.apache.avro.LogicalType;
import org.apache.avro.Schema;

public final class DurationMillisConversion extends Conversion<Duration> {
  private static final String DURATION_MILLIS = "duration-millis";
  private static final DurationMillis DURATION_MILLIS_TYPE = new DurationMillis();

  public static DurationMillis durationMillis() {
    return DURATION_MILLIS_TYPE;
  }

  @Override
  public Class<Duration> getConvertedType() {
    return Duration.class;
  }

  @Override
  public String getLogicalTypeName() {
    return DURATION_MILLIS;
  }

  @Override
  public String adjustAndSetValue(String varName, String valParamName) {
    return varName + " = " + valParamName + ".truncatedTo(java.time.temporal.ChronoUnit.MILLIS);";
  }

  @Override
  public Schema getRecommendedSchema() {
    return durationMillis().addToSchema(Schema.create(Schema.Type.LONG));
  }

  @Override
  public Duration fromLong(Long millisFromEpoch, Schema schema, LogicalType type) {
    return Duration.ofMillis(millisFromEpoch);
  }

  @Override
  public Long toLong(Duration timestamp, Schema schema, LogicalType type) {
    return timestamp.toMillis();
  }

  public static final class DurationMillis extends LogicalType {
    private DurationMillis() {
      super(DURATION_MILLIS);
    }

    @Override
    public void validate(Schema schema) {
      super.validate(schema);
      if (schema.getType() != Schema.Type.LONG) {
        throw new IllegalArgumentException(
            "Duration (millis) can only be used with an underlying long type");
      }
    }
  }
}
