package de.melsicon.kafka.sensors.topology;

import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorState.State;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.topology.ProcessorTestHelper.Advancement;
import java.time.Duration;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public final class ProcessorTest {
  private @MonotonicNonNull ValueTransformer<SensorState, SensorStateWithDuration> processor;

  @Before
  @EnsuresNonNull("processor")
  public void before() {
    processor = ProcessorTestHelper.createProcessor();
  }

  @After
  @RequiresNonNull("processor")
  public void after() {
    processor.close();
  }

  @Test
  @RequiresNonNull("processor")
  public void testSimple() {
    var initialState = ProcessorTestHelper.initial(State.OFF);
    var advancement = new Advancement(Duration.ofSeconds(30), State.ON);

    var newState = ProcessorTestHelper.advance(initialState, advancement);

    var result1 = processor.transform(initialState);
    var result2 = processor.transform(newState);

    ProcessorTestHelper.assertStateWithDuration(result1, null, Duration.ZERO);
    ProcessorTestHelper.assertStateWithDuration(result2, initialState, advancement.duration);
  }

  @Test
  @RequiresNonNull("processor")
  public void testRepeated() {
    var initialState = ProcessorTestHelper.initial(State.OFF);
    var advancement1 = new Advancement(Duration.ofSeconds(30), State.OFF);
    var advancement2 = new Advancement(Duration.ofSeconds(30), State.ON);
    var advancement3 = new Advancement(Duration.ofSeconds(15), State.OFF);

    var newState1 = ProcessorTestHelper.advance(initialState, advancement1);
    var newState2 = ProcessorTestHelper.advance(newState1, advancement2);
    var newState3 = ProcessorTestHelper.advance(newState2, advancement3);

    var result1 = processor.transform(initialState);
    var result2 = processor.transform(newState1);
    var result3 = processor.transform(newState2);
    var result4 = processor.transform(newState3);

    ProcessorTestHelper.assertStateWithDuration(result1, null, Duration.ZERO);
    ProcessorTestHelper.assertStateWithDuration(result2, initialState, advancement1.duration);
    ProcessorTestHelper.assertStateWithDuration(
        result3, initialState, advancement1.duration.plus(advancement2.duration));
    ProcessorTestHelper.assertStateWithDuration(result4, newState2, advancement3.duration);
  }

  @Test
  @RequiresNonNull("processor")
  public void nullHandling() {
    @SuppressWarnings("nullness:argument") // ValueTransformer is not annotated
    var result = processor.transform(null);
    ProcessorTestHelper.assertStateWithDuration(result, null, Duration.ZERO);
  }
}
