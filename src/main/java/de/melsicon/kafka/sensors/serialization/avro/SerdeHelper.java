package de.melsicon.kafka.sensors.serialization.avro;

import static org.apache.kafka.common.serialization.Serdes.serdeFrom;

import de.melsicon.kafka.sensors.serialization.mapping.MapFunction;
import de.melsicon.kafka.sensors.serialization.mapping.MappedDeserializer;
import de.melsicon.kafka.sensors.serialization.mapping.MappedSerializer;
import de.melsicon.kafka.sensors.type.avro.AvroDeserializer;
import de.melsicon.kafka.sensors.type.avro.AvroSerializer;
import org.apache.avro.message.MessageDecoder;
import org.apache.avro.message.MessageEncoder;
import org.apache.avro.message.SchemaStore;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

/* package */ final class SerdeHelper {
  private SerdeHelper() {}

  private static <U, T> Serializer<U> mappedSerializer(
      MessageEncoder<T> encoder, MapFunction<U, T> unmapper) {
    var serializer = new AvroSerializer<>(encoder);
    return new MappedSerializer<>(serializer, unmapper);
  }

  private static <U, T> Deserializer<U> mappedDeserializer(
      MessageDecoder<T> decoder, MapFunction<T, U> mapper) {
    var deserializer = new AvroDeserializer<>(decoder);
    return new MappedDeserializer<>(deserializer, mapper);
  }

  /* package */ static <U, T> Serde<U> createSerde(
      MessageEncoder<T> encoder,
      MessageDecoder<T> decoder,
      MapFunction<U, T> unmapper,
      MapFunction<T, U> mapper,
      SchemaStore resolver) {
    var mappedSerializer = mappedSerializer(encoder, unmapper);
    var mappedDeserializer = mappedDeserializer(decoder, mapper);
    return serdeFrom(mappedSerializer, mappedDeserializer);
  }
}
