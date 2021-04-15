package de.melsicon.kafka.serde.ion;

import com.amazon.ion.IonReader;
import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;
import com.amazon.ion.Timestamp;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorState.State;
import de.melsicon.kafka.model.SensorStateWithDuration;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

/* package */ final class IonSerializerHelper {
  private static final String FIELD_ID = "id";
  private static final String FIELD_STATE = "state";
  private static final String FIELD_TIME = "time";
  private static final String FIELD_EVENT = "event";
  private static final String FIELD_DURATION = "duration";

  private IonSerializerHelper() {}

  /* package */ static void serializeSensorStateWithDuration(
      IonWriter writer, SensorStateWithDuration message) throws IOException {
    writer.stepIn(IonType.STRUCT);
    writer.setFieldName(FIELD_EVENT);
    serializeSensorState(writer, message.getEvent());
    writer.setFieldName(FIELD_DURATION);
    writer.writeDecimal(duration2Decimal(message.getDuration()));
    writer.stepOut();
  }

  /* package */ static void serializeSensorState(IonWriter writer, SensorState message)
      throws IOException {
    writer.stepIn(IonType.STRUCT);
    writer.setFieldName(FIELD_ID);
    writer.writeString(message.getId());
    writer.setFieldName(FIELD_STATE);
    writer.writeSymbol(message.getState().name());
    writer.setFieldName(FIELD_TIME);
    var time = message.getTime();
    var timetamp = Timestamp.forEpochSecond(time.getEpochSecond(), time.getNano(), 0);
    writer.writeTimestamp(timetamp);
    writer.stepOut();
  }

  private static BigDecimal duration2Decimal(Duration duration) {
    var seconds = duration.getSeconds();
    var nano = duration.getNano();
    if (nano == 0) {
      return BigDecimal.valueOf(seconds);
    }
    var nanoOffset = BigDecimal.valueOf(nano).movePointLeft(9);
    return BigDecimal.valueOf(seconds).add(nanoOffset);
  }
  /* package */ static SensorStateWithDuration deserializeSensorStateWithDuration(
      IonReader reader) {
    reader.next();

    var builder = SensorStateWithDuration.builder();

    reader.stepIn();
    for (var type = reader.next(); type != null; type = reader.next()) {
      var name = reader.getFieldName();
      switch (name) {
        case FIELD_EVENT:
          builder.setEvent(deserializeSensorState2(reader));
          break;

        case FIELD_DURATION:
          var decimal = reader.bigDecimalValue();
          var components = decimal.divideAndRemainder(BigDecimal.ONE);
          var seconds = components[0].longValueExact();
          var nanoAdjustment = components[1].movePointRight(9).longValue();
          var duration = Duration.ofSeconds(seconds, nanoAdjustment);
          builder.setDuration(duration);
          break;

        default:
          // Ignore unknown fields
          break;
      }
    }
    reader.stepOut();

    return builder.build();
  }

  /* package */ static SensorState deserializeSensorState(IonReader reader) {
    reader.next();
    return deserializeSensorState2(reader);
  }

  private static SensorState deserializeSensorState2(IonReader reader) {
    var builder = SensorState.builder();

    reader.stepIn();
    for (var type = reader.next(); type != null; type = reader.next()) {
      var name = reader.getFieldName();
      switch (name) {
        case FIELD_ID:
          builder.setId(reader.stringValue());
          break;

        case FIELD_STATE:
          builder.setState(State.valueOf(reader.stringValue()));
          break;

        case FIELD_TIME:
          builder.setTime(readInstant(reader));
          break;

        default:
          // Ignore unknown fields
          break;
      }
    }
    reader.stepOut();

    return builder.build();
  }

  @SuppressWarnings("JdkObsolete")
  private static Instant readInstant(IonReader reader) {
    return reader.dateValue().toInstant();
  }
}
