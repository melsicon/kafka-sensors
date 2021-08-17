package de.melsicon.kafka.sensors.logic;

import de.melsicon.kafka.sensors.logic.CalculatorTestHelper.Advancement;
import de.melsicon.kafka.sensors.model.SensorState.State;
import java.io.IOException;
import java.time.Duration;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public final class CalculatorTest {
  private @MonotonicNonNull DurationCalculator calculator;

  @Before
  @EnsuresNonNull("calculator")
  public void before() {
    calculator = CalculatorTestHelper.createCalculator();
  }

  @After
  @RequiresNonNull("calculator")
  public void after() throws IOException {
    calculator.close();
  }

  @Test
  @RequiresNonNull("calculator")
  public void testSimple() {
    var initialState = CalculatorTestHelper.initial(State.OFF);
    var advancement = new Advancement(Duration.ofSeconds(30), State.ON);

    var newState = CalculatorTestHelper.advance(initialState, advancement);

    var result1 = calculator.transform(initialState);
    var result2 = calculator.transform(newState);

    CalculatorTestHelper.assertStateWithDuration(result1, null, Duration.ZERO);
    CalculatorTestHelper.assertStateWithDuration(result2, initialState, advancement.duration);
  }

  @Test
  @RequiresNonNull("calculator")
  public void testRepeated() {
    var initialState = CalculatorTestHelper.initial(State.OFF);
    var advancement1 = new Advancement(Duration.ofSeconds(30), State.OFF);
    var advancement2 = new Advancement(Duration.ofSeconds(30), State.ON);
    var advancement3 = new Advancement(Duration.ofSeconds(15), State.OFF);

    var newState1 = CalculatorTestHelper.advance(initialState, advancement1);
    var newState2 = CalculatorTestHelper.advance(newState1, advancement2);
    var newState3 = CalculatorTestHelper.advance(newState2, advancement3);

    var result1 = calculator.transform(initialState);
    var result2 = calculator.transform(newState1);
    var result3 = calculator.transform(newState2);
    var result4 = calculator.transform(newState3);

    CalculatorTestHelper.assertStateWithDuration(result1, null, Duration.ZERO);
    CalculatorTestHelper.assertStateWithDuration(result2, initialState, advancement1.duration);
    CalculatorTestHelper.assertStateWithDuration(
        result3, initialState, advancement1.duration.plus(advancement2.duration));
    CalculatorTestHelper.assertStateWithDuration(result4, newState2, advancement3.duration);
  }

  @Test
  @RequiresNonNull("calculator")
  public void nullHandling() {
    var result = calculator.transform(null);
    CalculatorTestHelper.assertStateWithDuration(result, null, Duration.ZERO);
  }
}
