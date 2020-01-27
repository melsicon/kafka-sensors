package de.melsicon.kafka.serde;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorState.State;
import de.melsicon.kafka.model.SensorStateWithDuration;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.kafka.common.serialization.Serde;

/* package */ final class TestHelper {
  /* package */ static final String REGISTRY_SCOPE = "test";
  /* package */ static final String KAFKA_TOPIC = "topic";

  private TestHelper() {}

  /* package */ static SensorState standardSensorState() {
    var instant = Instant.ofEpochSecond(443634300L);

    return SensorState.builder().setId("7331").setTime(instant).setState(State.OFF).build();
  }

  /* package */ static SensorStateWithDuration standardSensorStateWithDuration() {
    var event = standardSensorState();

    return SensorStateWithDuration.builder()
        .setEvent(event)
        .setDuration(Duration.ofSeconds(15))
        .build();
  }

  private static SensorStateSerdes[] serdes() {
    return new SensorStateSerdes[] {
      new de.melsicon.kafka.serde.json.JsonSerdes(),
      new de.melsicon.kafka.serde.proto.ProtoSerdes(),
      new de.melsicon.kafka.serde.avro.AvroSerdes(),
      new de.melsicon.kafka.serde.avro.ReflectSerdes(),
      new de.melsicon.kafka.serde.avro.GenericSerdes(),
      new de.melsicon.kafka.serde.confluent.AvroSerdes(),
      new de.melsicon.kafka.serde.confluent.ReflectSerdes(),
      new de.melsicon.kafka.serde.confluent.GenericSerdes()
    };
  }

  private static ImmutableMultimap<Format, SensorStateSerdes> serdesByFormat() {
    var serdesByFormat = ImmutableMultimap.<Format, SensorStateSerdes>builder();

    for (var serde : serdes()) {
      serdesByFormat.put(serde.format(), serde);
    }

    return serdesByFormat.build();
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
          assert inputSerdes.format() == resultSerdes.format();
          var o = new Object[3];
          o[0] = inputSerdes.name() + " - " + resultSerdes.name();
          o[1] = create.apply(inputSerdes);
          o[2] = create.apply(resultSerdes);
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
}
