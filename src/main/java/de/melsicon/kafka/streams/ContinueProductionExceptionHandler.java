package de.melsicon.kafka.streams;

import com.google.common.flogger.FluentLogger;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.errors.ProductionExceptionHandler;

/**
 * @link
 *     https://kafka.apache.org/documentation/streams/developer-guide/config-streams.html#default-production-exception-handler
 */
public final class ContinueProductionExceptionHandler implements ProductionExceptionHandler {
  private static final FluentLogger LOG = FluentLogger.forEnclosingClass();

  @Override
  public ProductionExceptionHandlerResponse handle(
      ProducerRecord<byte[], byte[]> record, Exception exception) {
    LOG.atWarning().withCause(exception).log("Processing error");
    return ProductionExceptionHandlerResponse.CONTINUE;
  }

  @Override
  public void configure(Map<String, ?> map) {}
}
