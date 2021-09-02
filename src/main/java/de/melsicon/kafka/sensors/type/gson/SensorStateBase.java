package de.melsicon.kafka.sensors.type.gson;

import java.time.Instant;

public interface SensorStateBase {
  String getId();

  Instant getTime();

  State getState();

  enum State {
    OFF,
    ON
  }
}
