package de.melsicon.kafka.sensors.serde;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorState.State;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.serde.context.TestComponent;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.kafka.common.serialization.Serde;

/* package */ final class TestHelper {
  /* package */ static final String KAFKA_TOPIC = "topic";

  private TestHelper() {}

  /* package */ static SensorState standardSensorState() {
    var instant = Instant.ofEpochSecond(443634300L);

    return SensorState.builder().id("7331").time(instant).state(State.ON).build();
  }

  /* package */ static SensorStateWithDuration standardSensorStateWithDuration() {
    var event = standardSensorState();

    return SensorStateWithDuration.builder().event(event).duration(Duration.ofSeconds(15)).build();
  }

  private static ImmutableMultimap<Format, NamedSerDes> serdesByFormat() {
    var builder = ImmutableMultimap.<Format, NamedSerDes>builder();

    var testComponent = TestComponent.create();
    var serdesByName = testComponent.sensorStateSerdesByName();

    serdesByName.forEach(
        (name, provider) -> {
          var serde = provider.get();
          var format = serde.format();
          builder.put(format, new NamedSerDes(name, serde));
        });

    return builder.build();
  }

  /**
   * This creates a list of all compatible {@link org.apache.kafka.common.serialization.Serializer}
   * - {@link org.apache.kafka.common.serialization.Deserializer} pairs to test.
   *
   * @return Serde pairs to test
   * @see org.junit.runners.Parameterized.Parameters
   */
  /* package */ static <T> ImmutableList<Object[]> createParameters(
      Function<SensorStateSerdes, Supplier<Serde<T>>> create) {

    var combinations = ImmutableList.<Object[]>builder();

    var groupedSerdes = serdesByFormat().asMap().values();
    for (var serdes : groupedSerdes) {
      for (var inputSerdes : serdes) {
        for (var resultSerdes : serdes) {
          assert inputSerdes.serdes.format() == resultSerdes.serdes.format();
          var o =
              new Object[] {
                inputSerdes.name + " - " + resultSerdes.name,
                create.apply(inputSerdes.serdes),
                create.apply(resultSerdes.serdes),
              };
          combinations.add(o);
        }
      }
    }

    return combinations.build();
  }

  /* package */ static ImmutableList<Object[]> parameters() {
    return createParameters(serdes -> serdes::createSensorStateSerde);
  }

  /* package */ static ImmutableList<Object[]> parametersWithDuration() {
    return createParameters(serdes -> serdes::createSensorStateWithDurationSerde);
  }

  private static final class NamedSerDes {
    public final String name;
    public final SensorStateSerdes serdes;

    private NamedSerDes(String name, SensorStateSerdes serdes) {
      this.name = name;
      this.serdes = serdes;
    }
  }
}
