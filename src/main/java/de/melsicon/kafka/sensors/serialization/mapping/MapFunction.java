package de.melsicon.kafka.sensors.serialization.mapping;

import org.checkerframework.checker.nullness.qual.PolyNull;

@FunctionalInterface
public interface MapFunction<T, R> {
  @PolyNull
  R apply(@PolyNull T t);
}
