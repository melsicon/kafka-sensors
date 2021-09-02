package de.melsicon.kafka.sensors.serialization.ion;

import static org.apache.kafka.common.serialization.Serdes.serdeFrom;

import com.amazon.ion.system.IonReaderBuilder;
import com.amazon.ion.system.IonWriterBuilder;
import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.type.ion.IonDeserializer;
import de.melsicon.kafka.sensors.type.ion.IonSerializer;
import de.melsicon.kafka.sensors.type.ion.IonSerializerHelper;
import org.apache.kafka.common.serialization.Serde;

/* package */ class IonSerDesHelper {
  private IonSerDesHelper() {}

  /* package */ static Serde<SensorState> createSensorStateSerde(
      IonWriterBuilder writerBuilder, IonReaderBuilder readerBuilder) {
    var serializer = new IonSerializer<>(writerBuilder, IonSerializerHelper::serializeSensorState);
    var deserializer =
        new IonDeserializer<>(readerBuilder, IonSerializerHelper::deserializeSensorState);

    return serdeFrom(serializer, deserializer);
  }

  /* package */ static Serde<SensorStateWithDuration> createSensorStateWithDurationSerde(
      IonWriterBuilder writerBuilder, IonReaderBuilder readerBuilder) {
    var serializer =
        new IonSerializer<>(writerBuilder, IonSerializerHelper::serializeSensorStateWithDuration);
    var deserializer =
        new IonDeserializer<>(
            readerBuilder, IonSerializerHelper::deserializeSensorStateWithDuration);

    return serdeFrom(serializer, deserializer);
  }
}
