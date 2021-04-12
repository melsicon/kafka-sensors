package de.melsicon.kafka.topology;

import com.google.common.annotations.VisibleForTesting;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import java.time.Duration;
import java.util.Optional;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* package */ final class DurationProcessor
    implements ValueTransformer<SensorState, SensorStateWithDuration> {
  public static final String SENSOR_STATES = "SensorStates";

  private @MonotonicNonNull KVStore<String, SensorState> store;

  /**
   * Wrap the old state with a duration how log it lasted.
   *
   * @param oldState The state of the sensor so far
   * @param sensorState The new state of the sensor
   * @return Wrapped old state with duration
   */
  private static SensorStateWithDuration addDuration(
      SensorState oldState, SensorState sensorState) {
    var duration = Duration.between(oldState.getTime(), sensorState.getTime());
    return SensorStateWithDuration.builder().setEvent(oldState).setDuration(duration).build();
  }

  /**
   * Check if we need to update the state in the state store.
   *
   * <p>Either we have no historical data, or the state has changed. We do not update for new events
   * with the same state.
   *
   * @param oldState The old sensor state
   * @param sensorState The new sensor state
   * @return True when we need to update
   */
  private static boolean needsUpdate(@Nullable SensorState oldState, SensorState sensorState) {
    if (oldState == null) {
      return true;
    }
    return oldState.getState() != sensorState.getState();
  }

  /**
   * Initialize with our {@link org.apache.kafka.streams.state.KeyValueStore}. Abstracted to enable
   * testing with a simple {@link java.util.Map}.
   *
   * @param store State store
   */
  @VisibleForTesting
  /* package */ void initStore(KVStore<String, SensorState> store) {
    this.store = store;
  }

  @Override
  public void init(ProcessorContext context) {
    var store = StoreHelper.<String, SensorState>stateStore(context, SENSOR_STATES);
    var kvStore = new StoreHelper.MappedStore<>(store);
    initStore(kvStore);
  }

  @Override
  @SuppressWarnings("nullness:override.return.invalid") // ValueTransformer is not annotated
  public @Nullable SensorStateWithDuration transform(@Nullable SensorState sensorState) {
    // init has to be called first
    assert store != null : "@AssumeAssertion(nullness): init not called";

    if (sensorState == null) {
      // Nothing to do
      return null;
    }

    // Check for the previous state, update if necessary
    var oldState = checkAndUpdateSensorState(sensorState);

    // When we have historical data, return duration so far. Otherwise, return null
    return oldState.map(state -> addDuration(state, sensorState)).orElse(null);
  }

  @Override
  public void close() {}

  /**
   * Checks the state store for historical state based on sensor ID and updates it, if necessary.
   *
   * <p>Modifies the state store as a side effect, so this method is not idempotent.
   *
   * @param sensorState The new sensor state
   * @return The old sensor state
   */
  @RequiresNonNull("store")
  private Optional<SensorState> checkAndUpdateSensorState(SensorState sensorState) {
    // The Sensor ID is our store key
    var sensorId = sensorState.getId();

    // Get the historical state (might be null)
    var oldState = store.get(sensorId);

    // Update the state store if necessary
    if (needsUpdate(oldState, sensorState)) {
      store.put(sensorId, sensorState);
    }

    // Return historical data
    return Optional.ofNullable(oldState);
  }
}
