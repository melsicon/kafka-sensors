package de.melsicon.kafka.sensors.serialization.avromapper;

import com.google.errorprone.annotations.Immutable;
import de.melsicon.kafka.sensors.type.avro.logicaltypes.DurationMicroHelper;
import java.time.Duration;

@Immutable
/* package */ final class DurationMapper {
  private DurationMapper() {}

  /* package */ static Duration milcros2Duration(long micros) {
    return DurationMicroHelper.micros2Duraion(micros);
  }

  /* package */ static long duration2Micros(Duration duration) {
    return DurationMicroHelper.duration2Micros(duration);
  }
}
