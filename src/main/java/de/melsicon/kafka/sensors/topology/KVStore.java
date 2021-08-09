package de.melsicon.kafka.sensors.topology;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Interface abstracting {@link org.apache.kafka.streams.state.KeyValueStore}, we can test with a
 * simple {@link java.util.Map}.
 *
 * @param <K> the type of keys maintained by this store
 * @param <V> the type of stored values
 */
public interface KVStore<K, V> {
  @Nullable
  V get(K key);

  void put(K key, V value);
}
