package de.melsicon.kafka.sensors.topology;

import de.melsicon.kafka.sensors.logic.DurationCalculator;
import de.melsicon.kafka.sensors.logic.DurationCalculatorFactory;
import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import javax.inject.Inject;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/* package */ final class DurationProcessor
    implements ValueTransformer<SensorState, SensorStateWithDuration> {
  public static final String SENSOR_STATES = "SensorStates";

  private final DurationCalculatorFactory calculatorFactory;

  private @MonotonicNonNull DurationCalculator calculator;

  @Inject
  /* package */ DurationProcessor(DurationCalculatorFactory calculatorFactory) {
    this.calculatorFactory = calculatorFactory;
  }

  @Override
  public void init(ProcessorContext context) {
    var kvStore = StoreHelper.<String, SensorState>stateStore(context, SENSOR_STATES);
    this.calculator = calculatorFactory.create(StoreHelper.mapStore(kvStore));
  }

  @Override
  @SuppressWarnings("nullness:override.return") // ValueTransformer is not annotated
  public @Nullable SensorStateWithDuration transform(@Nullable SensorState sensorState) {
    // init has to be called first
    assert calculator != null : "@AssumeAssertion(nullness): init not called";

    return calculator.transform(sensorState);
  }

  @Override
  public void close() {}
}
