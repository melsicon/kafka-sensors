package de.melsicon.kafka.sensors;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.openjdk.jmh.annotations.Mode.AverageTime;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode(AverageTime)
@OutputTimeUnit(NANOSECONDS)
@Fork(1)
@Warmup(iterations = 1, time = 5)
@Measurement(iterations = 1, time = 5)
public class Bench {
  @Benchmark
  public void serialize(ExecutionPlan plan) {
    plan.serializer.serialize(BenchHelper.TOPIC, plan.data);
  }

  @Benchmark
  public void deserialize(ExecutionPlan plan) {
    plan.deserializer.deserialize(BenchHelper.TOPIC, plan.bytes);
  }
}
