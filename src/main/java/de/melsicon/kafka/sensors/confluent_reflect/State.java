package de.melsicon.kafka.sensors.confluent_reflect;

import org.apache.avro.reflect.AvroAlias;
import org.apache.avro.reflect.AvroDoc;

@AvroDoc("New state of the sensor")
@AvroAlias(alias = "State", space = "de.melsicon.kafka.sensors.avro")
public enum State {
  OFF,
  ON
}
