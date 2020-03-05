package de.melsicon.kafka.topology;

import edu.umd.cs.findbugs.annotations.Nullable;
import org.apache.kafka.streams.errors.StreamsException;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

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
   * @param <K> the type of keys maintained by this store
   * @param <V> the type of stored values
   */
  /* package */ static final class MappedStore<K, V> implements KVStore<K, V> {
    private final KeyValueStore<K, V> store;

    /* package */ MappedStore(KeyValueStore<K, V> store) {
      this.store = store;
    }

    @Nullable
    @Override
    public V get(K key) {
      return store.get(key);
    }

    @Override
    public void put(K key, V value) {
      store.put(key, value);
    }
  }
}
