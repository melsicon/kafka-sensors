package de.melsicon.kafka.topology;

import static org.assertj.core.api.Assertions.assertThat;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorState.State;
import de.melsicon.kafka.model.SensorStateWithDuration;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public final class ProcessorTest {
  private static final String SENSOR_ID = "7331";

  private ValueTransformer<SensorState, SensorStateWithDuration> processor;

  private static <K, V> KVStore<K, V> map2KVStore(Map<K, V> store) {
    return new KVStore<>() {
      @Nullable
      @Override
      public V get(K key) {
        return store.get(key);
      }

      @Override
      public void put(K key, V value) {
        store.put(key, value);
      }
    };
  }

  private static SensorState initial(State state) {
    var instant = Instant.ofEpochSecond(443634300L);

    return SensorState.builder().setId(SENSOR_ID).setTime(instant).setState(state).build();
  }

  private static SensorState advance(SensorState old, Duration step, State state) {
    return SensorState.builder()
        .setId(old.getId())
        .setTime(old.getTime().plus(step))
        .setState(state)
        .build();
  }

  /**
   * Feeds {@code newState} into the processor, testing for correct transformation.
   *
   * @param newState New state
   * @param oldState Expected historic state
   * @param duration Expected duration the old state lasted
   * @throws AssertionError When the state is not transformed correctly
   */
  private void transformAndAssert(
      SensorState newState, @Nullable SensorState oldState, Duration duration) {
    var result = processor.transform(newState);

    if (oldState == null) {
      assertThat(result).isNull();
    } else {
      assertThat(result).isNotNull();
      assertThat(result.getEvent()).isEqualTo(oldState);
      assertThat(result.getDuration()).isEqualTo(duration);
    }
  }

  @Before
  public void before() {
    var store = new HashMap<String, SensorState>();
    var kvStore = map2KVStore(store);

    var processor = new DurationProcessor();
    processor.initStore(kvStore);
    this.processor = processor;
  }

  @After
  public void after() {}

  @Test
  public void testSimple() {
    var initialState = initial(State.OFF);

    var result1 = processor.transform(initialState);

    assertThat(result1).isNull();

    var step1 = Duration.ofSeconds(30);
    var newState = advance(initialState, step1, State.ON);

    transformAndAssert(newState, initialState, step1);
  }

  @Test
  public void testRepeated() {
    var initialState = initial(State.OFF);

    transformAndAssert(initialState, null, Duration.ZERO);

    var step1 = Duration.ofSeconds(30);
    var newState = advance(initialState, step1, State.OFF);

    transformAndAssert(newState, initialState, step1);

    var step2 = Duration.ofSeconds(30);
    var newState2 = advance(newState, step2, State.ON);

    transformAndAssert(newState2, initialState, step1.plus(step2));

    var step3 = Duration.ofSeconds(15);
    var newState3 = advance(newState2, step3, State.OFF);

    transformAndAssert(newState3, newState2, step3);
  }

  @Test
  public void nullHandling() {
    var result = processor.transform(null);
    assertThat(result).isNull();
  }
}
