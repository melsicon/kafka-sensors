package de.melsicon.kafka.serialization.protobuf;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.protobuf.Timestamp;
import de.melsicon.kafka.sensors.v1.SensorState;
import de.melsicon.kafka.sensors.v1.SensorState.State;
import java.io.IOException;
import java.time.Instant;
import org.junit.Test;

public final class SerializationTest {
  @Test
  public void canDecode() throws IOException {
    var instant = Instant.ofEpochSecond(443634300L);
    var time =
        Timestamp.newBuilder()
            .setSeconds(instant.getEpochSecond())
            .setNanos(instant.getNano())
            .build();

    var sensorState =
        SensorState.newBuilder().setId("7331").setTime(time).setState(State.STATE_OFF).build();

    var encoded = sensorState.toByteArray();

    var decoded = SensorState.parseFrom(encoded);
    assertThat(decoded).isEqualTo(sensorState);
  }
}
