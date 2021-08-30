package de.melsicon.kafka.sensors.type.avro.logicaltypes;

import java.time.Duration;
import org.apache.avro.Conversion;
import org.apache.avro.LogicalType;
import org.apache.avro.Schema;

public final class DurationMicrosConversion extends Conversion<Duration> {
  private static final String DURATION_MICROS = "duration-micros";
  private static final DurationMicros DURATION_MICROS_TYPE = new DurationMicros();

  public static DurationMicros durationMicros() {
    return DURATION_MICROS_TYPE;
  }

  @Override
  public Class<Duration> getConvertedType() {
    return Duration.class;
  }

  @Override
  public String getLogicalTypeName() {
    return DURATION_MICROS;
  }

  @Override
  public String adjustAndSetValue(String varName, String valParamName) {
    return varName + " = " + valParamName + ".truncatedTo(java.time.temporal.ChronoUnit.MICROS);";
  }

  @Override
  public Schema getRecommendedSchema() {
    return durationMicros().addToSchema(Schema.create(Schema.Type.LONG));
  }

  @Override
  public Duration fromLong(Long micros, Schema schema, LogicalType type) {
    return DurationMicroHelper.fromLong(micros);
  }

  @Override
  public Long toLong(Duration duration, Schema schema, LogicalType type) {
    return DurationMicroHelper.toLong(duration);
  }

  public static final class DurationMicros extends LogicalType {
    private DurationMicros() {
      super(DURATION_MICROS);
    }

    @Override
    public void validate(Schema schema) {
      super.validate(schema);
      if (schema.getType() != Schema.Type.LONG) {
        throw new IllegalArgumentException(
            "Duration (micros) can only be used with an underlying long type");
      }
    }
  }
}
