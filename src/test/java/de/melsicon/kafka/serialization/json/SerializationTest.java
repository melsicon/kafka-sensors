package de.melsicon.kafka.serialization.json;

import static com.google.common.truth.Truth.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorState.State;
import de.melsicon.kafka.serde.json.JsonHelper;
import java.io.IOException;
import java.time.Instant;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("nullness:initialization.fields.uninitialized") // Initialized in before
public final class SerializationTest {
  private ObjectMapper mapper;

  @Before
  public void before() {
    mapper = JsonHelper.mapper();
  }

  @Test
  public void canDecode() throws IOException {
    var instant = Instant.ofEpochSecond(443634300L);

    var sensorState =
        SensorState.builder().setId("7331").setTime(instant).setState(State.OFF).build();

    var encoded = mapper.writeValueAsBytes(sensorState);

    var decoded = mapper.readValue(encoded, SensorState.class);
    assertThat(decoded).isEqualTo(sensorState);
  }
}
