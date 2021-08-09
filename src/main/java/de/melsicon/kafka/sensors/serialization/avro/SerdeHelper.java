package de.melsicon.kafka.sensors.serialization.avro;

import static org.apache.kafka.common.serialization.Serdes.serdeFrom;

import de.melsicon.kafka.sensors.serialization.mapping.MapFunction;
import de.melsicon.kafka.sensors.serialization.mapping.MappedDeserializer;
import de.melsicon.kafka.sensors.serialization.mapping.MappedSerializer;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.SchemaStore;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

/* package */ final class SerdeHelper {
  private SerdeHelper() {}

  private static <U, T> Serializer<U> mappedSerializer(
      GenericData model, Schema schema, MapFunction<U, T> unmapper) {
    var encoder = new BinaryMessageEncoder<T>(model, schema);
    var serializer = new AvroSerializer<>(encoder);
    return new MappedSerializer<>(serializer, unmapper);
  }

  private static <U, T> Deserializer<U> mappedDeserializer(
      GenericData model, Schema schema, MapFunction<T, U> mapper, SchemaStore resolver) {
    var decoder = new BinaryMessageDecoder<T>(model, schema, resolver);
    var deserializer = new AvroDeserializer<>(decoder);
    return new MappedDeserializer<>(deserializer, mapper);
  }

  /* package */ static <U, T> Serde<U> createSerde(
      GenericData model,
      Schema schema,
      MapFunction<U, T> unmapper,
      MapFunction<T, U> mapper,
      SchemaStore resolver) {
    var mappedSerializer = mappedSerializer(model, schema, unmapper);
    var mappedDeserializer = mappedDeserializer(model, schema, mapper, resolver);
    return serdeFrom(mappedSerializer, mappedDeserializer);
  }
}
