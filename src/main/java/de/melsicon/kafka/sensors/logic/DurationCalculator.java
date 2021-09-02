package de.melsicon.kafka.sensors.logic;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.Optional;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class DurationCalculator implements Closeable {
  private final KVStore<String, SensorState> store;

  @AssistedInject
  /* package */ DurationCalculator(@Assisted KVStore<String, SensorState> store) {
    this.store = store;
  }

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
    return SensorStateWithDuration.builder().event(oldState).duration(duration).build();
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
    return oldState == null || oldState.getState() != sensorState.getState();
  }

  @CanIgnoreReturnValue
  public @Nullable SensorStateWithDuration transform(@Nullable SensorState sensorState) {
    if (sensorState == null) {
      // Nothing to do
      return null;
    }

    // Check for the previous state, update if necessary
    var oldState = checkAndUpdateSensorState(sensorState);

    // When we have historical data, return duration so far. Otherwise, return null
    return oldState.map(state -> addDuration(state, sensorState)).orElse(null);
  }

  /**
   * Checks the state store for historical state based on sensor ID and updates it, if necessary.
   *
   * <p>Modifies the state store as a side effect, so this method is not idempotent.
   *
   * @param sensorState The new sensor state
   * @return The old sensor state
   */
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

  @Override
  public void close() throws IOException {
    store.close();
  }
}
