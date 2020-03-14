package de.melsicon.kafka.topology;

import static de.melsicon.kafka.topology.ProcessorTestHelper.advance;
import static de.melsicon.kafka.topology.ProcessorTestHelper.assertStateWithDuration;
import static de.melsicon.kafka.topology.ProcessorTestHelper.createProcessor;
import static de.melsicon.kafka.topology.ProcessorTestHelper.initial;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorState.State;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.topology.ProcessorTestHelper.Advancement;
import java.time.Duration;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public final class ProcessorTest {
  private ValueTransformer<SensorState, SensorStateWithDuration> processor;

  @Before
  public void before() {
    processor = createProcessor();
  }

  @After
  public void after() {
    processor.close();
  }

  @Test
  public void testSimple() {
    var initialState = initial(State.OFF);
    var advancement = new Advancement(Duration.ofSeconds(30), State.ON);

    var newState = advance(initialState, advancement);

    var result1 = processor.transform(initialState);
    var result2 = processor.transform(newState);

    assertStateWithDuration(result1, null, Duration.ZERO);
    assertStateWithDuration(result2, initialState, advancement.duration);
  }

  @Test
  public void testRepeated() {
    var initialState = initial(State.OFF);
    var advancement1 = new Advancement(Duration.ofSeconds(30), State.OFF);
    var advancement2 = new Advancement(Duration.ofSeconds(30), State.ON);
    var advancement3 = new Advancement(Duration.ofSeconds(15), State.OFF);

    var newState1 = advance(initialState, advancement1);
    var newState2 = advance(newState1, advancement2);
    var newState3 = advance(newState2, advancement3);

    var result1 = processor.transform(initialState);
    var result2 = processor.transform(newState1);
    var result3 = processor.transform(newState2);
    var result4 = processor.transform(newState3);

    assertStateWithDuration(result1, null, Duration.ZERO);
    assertStateWithDuration(result2, initialState, advancement1.duration);
    assertStateWithDuration(
        result3, initialState, advancement1.duration.plus(advancement2.duration));
    assertStateWithDuration(result4, newState2, advancement3.duration);
  }

  @Test
  public void nullHandling() {
    var result = processor.transform(null);
    assertStateWithDuration(result, null, Duration.ZERO);
  }
}
