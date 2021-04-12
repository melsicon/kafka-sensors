package de.melsicon.kafka.topology;

import static com.google.common.truth.Truth.assertThat;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorState.State;
import de.melsicon.kafka.model.SensorStateWithDuration;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/* package */ final class ProcessorTestHelper {
  private static final String SENSOR_ID = "7331";

  private ProcessorTestHelper() {}

  /* package */ static DurationProcessor createProcessor() {
    var store = new HashMap<String, SensorState>();
    var kvStore = new MapStore<>(store);

    var processor = new DurationProcessor();
    processor.initStore(kvStore);
    return processor;
  }

  static SensorState initial(State state) {
    var instant = Instant.ofEpochSecond(443634300L);

    return SensorState.builder().setId(SENSOR_ID).setTime(instant).setState(state).build();
  }

  static SensorState advance(SensorState old, Advancement advancement) {
    return SensorState.builder()
        .setId(old.getId())
        .setTime(old.getTime().plus(advancement.duration))
        .setState(advancement.state)
        .build();
  }

  /**
   * Test for correct transformation.
   *
   * @param result Transformation processor result
   * @param duration Expected duration the old state lasted
   * @throws AssertionError When the state is not transformed correctly
   */
  /* package */ static void assertStateWithDuration(
      @Nullable SensorStateWithDuration result,
      @Nullable SensorState expectedState,
      Duration duration) {
    var resultState = result == null ? null : result.getEvent();
    assertThat(resultState).isEqualTo(expectedState);

    var resultDuration = result == null ? Duration.ZERO : result.getDuration();
    assertThat(resultDuration).isEqualTo(duration);
  }

  /* package */ static final class Advancement {
    public final Duration duration;
    public final State state;

    /* package */ Advancement(Duration duration, State state) {
      this.duration = duration;
      this.state = state;
    }
  }

  /* package */ static final class MapStore<K, V> implements KVStore<@NonNull K, V> {
    private final Map<K, V> store;

    /* package */ MapStore(Map<K, V> store) {
      this.store = store;
    }

    @Override
    public @Nullable V get(@NonNull K key) {
      return store.get(key);
    }

    @Override
    public void put(K key, V value) {
      store.put(key, value);
    }
  }
}
