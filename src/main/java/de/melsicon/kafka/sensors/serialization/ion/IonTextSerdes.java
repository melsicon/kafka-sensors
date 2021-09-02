package de.melsicon.kafka.sensors.serialization.ion;

import com.amazon.ion.system.IonReaderBuilder;
import com.amazon.ion.system.IonTextWriterBuilder;
import com.amazon.ion.system.IonWriterBuilder;
import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.serde.Format;
import de.melsicon.kafka.sensors.serde.SensorStateSerdes;
import javax.inject.Inject;
import org.apache.kafka.common.serialization.Serde;

public final class IonTextSerdes implements SensorStateSerdes {
  private final IonWriterBuilder writerBuilder;
  private final IonReaderBuilder readerBuilder;

  @Inject
  /* package */ IonTextSerdes() {
    this.writerBuilder = IonTextWriterBuilder.standard().immutable();
    this.readerBuilder = IonReaderBuilder.standard().immutable();
  }

  @Override
  public Format format() {
    return Format.ION;
  }

  @Override
  public Serde<SensorState> createSensorStateSerde() {
    return IonSerDesHelper.createSensorStateSerde(writerBuilder, readerBuilder);
  }

  @Override
  public Serde<SensorStateWithDuration> createSensorStateWithDurationSerde() {
    return IonSerDesHelper.createSensorStateWithDurationSerde(writerBuilder, readerBuilder);
  }
}
