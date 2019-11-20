package de.melsicon.kafka.topology;

import org.apache.kafka.streams.errors.StreamsException;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

/* package */ final class StoreHelper {
  private StoreHelper() {}

  @SuppressWarnings("unchecked")
  /* package */ static <K, V> KeyValueStore<K, V> stateStore(
      ProcessorContext context, String storeName) {
    var store = context.getStateStore(storeName);
    if (store instanceof KeyValueStore<?, ?>) {
      return (KeyValueStore<K, V>) store;
    }
    throw new StreamsException(String.format("Store %s missing", storeName));
  }
}
