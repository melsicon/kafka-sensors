package de.melsicon.kafka.sensors;

import de.melsicon.kafka.sensors.BenchHelper.ExecutionPlan;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 3, time = 5)
@Measurement(iterations = 5, time = 10)
public class Bench {
  @Benchmark
  public void serialize(ExecutionPlan plan) {
    plan.serializer.serialize(null, plan.data);
  }

  @Benchmark
  public void deserialize(ExecutionPlan plan) {
    plan.deserializer.deserialize(null, plan.bytes);
  }
}
