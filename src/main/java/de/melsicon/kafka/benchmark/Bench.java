package de.melsicon.kafka.benchmark;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.openjdk.jmh.annotations.Mode.AverageTime;

import de.melsicon.kafka.benchmark.serdes.Constants;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode(AverageTime)
@OutputTimeUnit(NANOSECONDS)
@Fork(1)
@Warmup(iterations = 3, time = 5)
@Measurement(iterations = 5, time = 5)
public class Bench {
  @Benchmark
  @RequiresNonNull({"#1.serializer", "#1.data"})
  public void serialize(ExecutionPlan plan) {
    plan.serializer.serialize(Constants.TOPIC, plan.data);
  }

  @Benchmark
  @RequiresNonNull({"#1.deserializer", "#1.serialized"})
  public void deserialize(ExecutionPlan plan) {
    plan.deserializer.deserialize(Constants.TOPIC, plan.serialized);
  }
}
