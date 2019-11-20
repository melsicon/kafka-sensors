package de.melsicon.kafka.streams;

import com.google.common.flogger.FluentLogger;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.errors.DeserializationExceptionHandler;
import org.apache.kafka.streams.processor.ProcessorContext;

/**
 * @link
 *     https://kafka.apache.org/documentation/streams/developer-guide/config-streams.html#default-deserialization-exception-handler
 */
public final class ContinueDeserializationExceptionHandler
    implements DeserializationExceptionHandler {
  private static final FluentLogger LOG = FluentLogger.forEnclosingClass();

  @Override
  public DeserializationHandlerResponse handle(
      ProcessorContext context, ConsumerRecord<byte[], byte[]> record, Exception exception) {
    LOG.atWarning().withCause(exception).log(
        "Deserialization error at topic %s partition %d offset %d",
        context.topic(), context.partition(), context.offset());
    return DeserializationHandlerResponse.CONTINUE;
  }

  @Override
  public void configure(Map<String, ?> map) {}
}
