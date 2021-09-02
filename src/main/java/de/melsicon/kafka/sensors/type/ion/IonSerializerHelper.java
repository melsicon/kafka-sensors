package de.melsicon.kafka.sensors.type.ion;

import com.amazon.ion.IonReader;
import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;
import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorState.State;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.type.helper.DurationDecimalHelper;
import de.melsicon.kafka.sensors.type.helper.InstantDecimalHelper;
import java.io.IOException;

public final class IonSerializerHelper {
  private static final String FIELD_ID = "id";
  private static final String FIELD_STATE = "state";
  private static final String FIELD_TIME = "time";
  private static final String FIELD_EVENT = "event";
  private static final String FIELD_DURATION = "duration";

  private IonSerializerHelper() {}

  public static void serializeSensorStateWithDuration(
      IonWriter writer, SensorStateWithDuration message) throws IOException {
    writer.stepIn(IonType.STRUCT);
    writer.setFieldName(FIELD_EVENT);
    serializeSensorState(writer, message.getEvent());
    writer.setFieldName(FIELD_DURATION);
    writer.writeDecimal(DurationDecimalHelper.duration2Decimal(message.getDuration()));
    writer.stepOut();
  }

  public static void serializeSensorState(IonWriter writer, SensorState message)
      throws IOException {
    writer.stepIn(IonType.STRUCT);
    writer.setFieldName(FIELD_ID);
    writer.writeString(message.getId());
    writer.setFieldName(FIELD_STATE);
    writer.writeSymbol(message.getState().name());
    writer.setFieldName(FIELD_TIME);
    writer.writeDecimal(InstantDecimalHelper.instant2Decimal(message.getTime()));
    writer.stepOut();
  }

  public static SensorStateWithDuration deserializeSensorStateWithDuration(IonReader reader) {
    reader.next();

    var builder = SensorStateWithDuration.builder();

    reader.stepIn();
    for (var type = reader.next(); type != null; type = reader.next()) {
      var name = reader.getFieldName();
      switch (name) {
        case FIELD_EVENT:
          builder.event(deserializeSensorState2(reader));
          break;

        case FIELD_DURATION:
          builder.duration(DurationDecimalHelper.decimal2Duration(reader.bigDecimalValue()));
          break;

        default:
          // Ignore unknown fields
          break;
      }
    }
    reader.stepOut();

    return builder.build();
  }

  public static SensorState deserializeSensorState(IonReader reader) {
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
          builder.id(reader.stringValue());
          break;

        case FIELD_STATE:
          builder.state(State.valueOf(reader.stringValue()));
          break;

        case FIELD_TIME:
          builder.time(InstantDecimalHelper.decimal2Instant(reader.bigDecimalValue()));
          break;

        default:
          // Ignore unknown fields
          break;
      }
    }
    reader.stepOut();

    return builder.build();
  }
}
