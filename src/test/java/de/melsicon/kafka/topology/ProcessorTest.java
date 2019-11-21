package de.melsicon.kafka.topology;

import static org.assertj.core.api.Assertions.assertThat;

import de.melsicon.annotation.Initializer;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorState.State;
import de.melsicon.kafka.model.SensorStateWithDuration;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public final class ProcessorTest {
  private ValueTransformer<SensorState, SensorStateWithDuration> processor;

  @Before
  @Initializer
  public void before() {
    var store = new HashMap<String, SensorState>();
    var processor = new DurationProcessor();
    processor.initStore(store::get, store::put);
    this.processor = processor;
  }

  @After
  public void after() {}

  @Test
  public void testSimple() {
    var instant = Instant.ofEpochSecond(443634300L);

    var initialState =
        SensorState.builder().setId("7331").setTime(instant).setState(State.OFF).build();

    var result1 = processor.transform(initialState);

    assertThat(result1).isNull();

    var next = instant.plusSeconds(30);
    var newState = SensorState.builder().setId("7331").setTime(next).setState(State.ON).build();

    var result2 = processor.transform(newState);

    assertThat(result2).isNotNull();
    assertThat(result2.getEvent()).isEqualTo(initialState);
    assertThat(result2.getDuration()).isEqualTo(Duration.ofSeconds(30));
  }

  @Test
  public void testRepeated() {
    var instant = Instant.ofEpochSecond(443634300L);

    var initialState =
        SensorState.builder().setId("7331").setTime(instant).setState(State.OFF).build();

    var result1 = processor.transform(initialState);

    assertThat(result1).isNull();

    var next = instant.plusSeconds(30);
    var newState = SensorState.builder().setId("7331").setTime(next).setState(State.OFF).build();

    var result2 = processor.transform(newState);

    assertThat(result2).isNotNull();
    assertThat(result2.getEvent()).isEqualTo(initialState);
    assertThat(result2.getDuration()).isEqualTo(Duration.ofSeconds(30));

    var next2 = next.plusSeconds(30);
    var newState2 = SensorState.builder().setId("7331").setTime(next2).setState(State.ON).build();

    var result3 = processor.transform(newState2);

    assertThat(result3).isNotNull();
    assertThat(result3.getEvent()).isEqualTo(initialState);
    assertThat(result3.getDuration()).isEqualTo(Duration.ofSeconds(60));

    var next3 = next2.plusSeconds(15);
    var newState3 = SensorState.builder().setId("7331").setTime(next3).setState(State.OFF).build();

    var result4 = processor.transform(newState3);

    assertThat(result4).isNotNull();
    assertThat(result4.getEvent()).isEqualTo(newState2);
    assertThat(result4.getDuration()).isEqualTo(Duration.ofSeconds(15));
  }

  @Test
  public void nullHandling() {
    var result = processor.transform(null);
    assertThat(result).isNull();
  }
}
