package de.melsicon.kafka.sensors.v1;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.protobuf.Timestamp;
import de.melsicon.kafka.sensors.v1.SensorState.State;
import de.melsicon.kafka.serde.proto.ProtoDeserializer;
import de.melsicon.kafka.serde.proto.ProtoSerializer;
import java.time.Instant;
import org.junit.Test;

public final class SensorStateTest {
  private static final String TOPIC = "topic";

  @Test
  public void canDecode() {
    var instant = Instant.ofEpochSecond(443634300L);

    var sensorState =
        SensorState.newBuilder()
            .setId("7331")
            .setTime(
                Timestamp.newBuilder()
                    .setSeconds(instant.getEpochSecond())
                    .setNanos(instant.getNano()))
            .setState(State.STATE_OFF)
            .build();

    byte[] encoded;
    try (var serializer = new ProtoSerializer<SensorState>()) {
      encoded = serializer.serialize(TOPIC, sensorState);
    }

    SensorState decoded;
    try (var deserializer = new ProtoDeserializer<>(SensorState.parser())) {
      decoded = deserializer.deserialize(TOPIC, encoded);
    }

    assertThat(decoded).isEqualTo(sensorState);
  }
}
