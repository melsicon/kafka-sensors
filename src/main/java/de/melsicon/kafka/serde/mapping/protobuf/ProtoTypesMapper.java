package de.melsicon.kafka.serde.mapping.protobuf;

import com.google.protobuf.Timestamp;
import java.time.Duration;
import java.time.Instant;

public final class ProtoTypesMapper {
  private ProtoTypesMapper() {}

  public static Instant timestamp2Instant(Timestamp timestamp) {
    return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
  }

  public static Timestamp instant2Timestamp(Instant instant) {
    return Timestamp.newBuilder()
        .setSeconds(instant.getEpochSecond())
        .setNanos(instant.getNano())
        .build();
  }

  public static Duration duration2Duration(com.google.protobuf.Duration duration) {
    return Duration.ofSeconds(duration.getSeconds(), duration.getNanos());
  }

  public static com.google.protobuf.Duration duration2Duration(Duration duration) {
    return com.google.protobuf.Duration.newBuilder()
        .setSeconds(duration.getSeconds())
        .setNanos(duration.getNano())
        .build();
  }
}
