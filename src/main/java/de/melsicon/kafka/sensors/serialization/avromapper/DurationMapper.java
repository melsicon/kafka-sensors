package de.melsicon.kafka.sensors.serialization.avromapper;

import de.melsicon.kafka.sensors.serialization.logicaltypes.DurationMicroHelper;
import java.time.Duration;

/* package */ final class DurationMapper {
  private DurationMapper() {}

  /* package */ static Duration milcros2Duration(long micros) {
    return DurationMicroHelper.fromLong(micros);
  }

  /* package */ static long duration2Micros(Duration duration) {
    return DurationMicroHelper.toLong(duration);
  }
}
