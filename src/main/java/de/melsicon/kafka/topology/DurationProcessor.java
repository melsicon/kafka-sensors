package de.melsicon.kafka.topology;

import de.melsicon.annotation.Initializer;
import de.melsicon.annotation.Nullable;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import java.time.Duration;
import java.util.Optional;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

public final class DurationProcessor
    implements ValueTransformer<@Nullable SensorState, @Nullable SensorStateWithDuration> {
  public static final String SENSOR_STATES = "SensorStates";

  private KeyValueStore<String, SensorState> store;

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
   * <p>Either we have no historical data, or the state has changed.
   *
   * @param oldState The old sensor state
   * @param sensorState The new sensor state
   * @return Flag whether we need to update
   */
  private static boolean neetToUpdate(@Nullable SensorState oldState, SensorState sensorState) {
    return oldState == null || oldState.getState() != sensorState.getState();
  }

  @Override
  @Initializer
  public void init(ProcessorContext context) {
    this.store = StoreHelper.stateStore(context, SENSOR_STATES);
  }

  @Override
  public @Nullable SensorStateWithDuration transform(@Nullable SensorState sensorState) {
    // Nothing to do
    if (sensorState == null) {
      return null;
    }

    // Check for the previous state, update if necessary
    var oldState = checkAndUpdateSensorState(sensorState);

    // When we have historical data, return duration so far. Otherwise return null
    return oldState.map(state -> addDuration(state, sensorState)).orElse(null);
  }

  /**
   * Checks the state store for historical state based on sensor ID and updates it, if necessary.
   *
   * @param sensorState The new sensor state
   * @return The old sensor state
   */
  private Optional<SensorState> checkAndUpdateSensorState(SensorState sensorState) {
    // The Sensor ID is our index
    var index = sensorState.getId();

    // Get the historical state (might be null)
    var oldState = store.get(index);
    if (neetToUpdate(oldState, sensorState)) {
      // Update the state store to the new state
      store.put(index, sensorState);
    }
    return Optional.ofNullable(oldState);
  }

  @Override
  public void close() {}
}
