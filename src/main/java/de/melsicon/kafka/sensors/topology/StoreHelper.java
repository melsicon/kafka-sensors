package de.melsicon.kafka.sensors.topology;

import de.melsicon.kafka.sensors.logic.KVStore;
import org.apache.kafka.streams.errors.StreamsException;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.checkerframework.checker.nullness.qual.Nullable;

/* package */ final class StoreHelper {
  private StoreHelper() {}

  /**
   * Retrieve a {@link KeyValueStore} from a {@link ProcessorContext}. Used to encapsulate the
   * unsafe cast and make things a little more type safe.
   *
   * @param context The processor context to get the store from
   * @param name The store name
   * @param <K> the type of keys maintained by this store
   * @param <V> the type of stored values
   * @return The state store instance
   */
  @SuppressWarnings("unchecked")
  /* package */ static <K, V> KeyValueStore<K, V> stateStore(
      ProcessorContext context, String name) {
    var store = context.getStateStore(name);
    if (store instanceof KeyValueStore<?, ?>) {
      return (KeyValueStore<K, V>) store;
    }
    throw new StreamsException(String.format("Store %s missing", name));
  }

  /**
   * Wrap a {@link KeyValueStore} in a {@link KVStore}.
   *
   * @param store The underlying {@link KeyValueStore} implementation
   * @param <K> the type of keys maintained by this store
   * @param <V> the type of stored values
   * @return The wrapped store
   */
  /* package */ static <K, V> KVStore<K, V> mapStore(KeyValueStore<K, V> store) {
    return new StoreAdapter<>(store);
  }

  private static final class StoreAdapter<K, V> implements KVStore<K, V> {
    private final KeyValueStore<K, V> store;

    /* package */ StoreAdapter(KeyValueStore<K, V> store) {
      this.store = store;
    }

    @Override
    public @Nullable V get(K key) {
      return store.get(key);
    }

    @Override
    public void put(K key, V value) {
      store.put(key, value);
    }

    @Override
    public void close() {
      store.close();
    }
  }
}
