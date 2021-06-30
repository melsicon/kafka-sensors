package de.melsicon.kafka.serde.ion;

import static org.apache.kafka.common.serialization.Serdes.serdeFrom;

import com.amazon.ion.system.IonBinaryWriterBuilder;
import com.amazon.ion.system.IonReaderBuilder;
import com.amazon.ion.system.IonTextWriterBuilder;
import com.amazon.ion.system.IonWriterBuilder;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.Format;
import de.melsicon.kafka.serde.SensorStateSerdes;
import org.apache.kafka.common.serialization.Serde;

public final class IonSerdes implements SensorStateSerdes {
  private final IonWriterBuilder writerBuilder;
  private final IonReaderBuilder readerBuilder;
  private final String name;

  private IonSerdes(IonWriterBuilder writerBuilder, String name) {
    this.writerBuilder = writerBuilder;
    this.readerBuilder = IonReaderBuilder.standard().immutable();
    this.name = name;
  }

  public static IonSerdes binarySerdes() {
    return new IonSerdes(IonBinaryWriterBuilder.standard().immutable(), "ion-binary");
  }

  public static IonSerdes textSerdes() {
    return new IonSerdes(IonTextWriterBuilder.standard().immutable(), "ion-text");
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public Format format() {
    return Format.ION;
  }

  @Override
  public Serde<SensorState> createSensorStateSerde() {
    var serializer = new IonSerializer<>(writerBuilder, IonSerializerHelper::serializeSensorState);
    var deserializer =
        new IonDeserializer<>(readerBuilder, IonSerializerHelper::deserializeSensorState);

    return serdeFrom(serializer, deserializer);
  }

  @Override
  public Serde<SensorStateWithDuration> createSensorStateWithDurationSerde() {
    var serializer =
        new IonSerializer<>(writerBuilder, IonSerializerHelper::serializeSensorStateWithDuration);
    var deserializer =
        new IonDeserializer<>(
            readerBuilder, IonSerializerHelper::deserializeSensorStateWithDuration);

    return serdeFrom(serializer, deserializer);
  }
}
