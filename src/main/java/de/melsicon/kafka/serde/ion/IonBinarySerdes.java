package de.melsicon.kafka.serde.ion;

import com.amazon.ion.system.IonBinaryWriterBuilder;
import com.amazon.ion.system.IonReaderBuilder;
import com.amazon.ion.system.IonWriterBuilder;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.Format;
import de.melsicon.kafka.serde.SensorStateSerdes;
import javax.inject.Inject;
import org.apache.kafka.common.serialization.Serde;

public final class IonBinarySerdes implements SensorStateSerdes {
  private final IonWriterBuilder writerBuilder;
  private final IonReaderBuilder readerBuilder;

  @Inject
  /* package */ IonBinarySerdes() {
    this.writerBuilder = IonBinaryWriterBuilder.standard().immutable();
    this.readerBuilder = IonReaderBuilder.standard().immutable();
  }

  @Override
  public Format format() {
    return Format.ION;
  }

  @Override
  public Serde<SensorState> createSensorStateSerde() {
    return IonSerializerHelper.createSensorStateSerde(writerBuilder, readerBuilder);
  }

  @Override
  public Serde<SensorStateWithDuration> createSensorStateWithDurationSerde() {
    return IonSerializerHelper.createSensorStateWithDurationSerde(writerBuilder, readerBuilder);
  }
}
