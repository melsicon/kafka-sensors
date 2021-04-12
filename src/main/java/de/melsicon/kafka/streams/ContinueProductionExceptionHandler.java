package de.melsicon.kafka.streams;

import com.google.common.flogger.FluentLogger;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.errors.ProductionExceptionHandler;

/**
 * {@link ProductionExceptionHandler} that logs an exception while attempting to produce result
 * records and then signals the processing pipeline to continue processing more records.
 */
public final class ContinueProductionExceptionHandler implements ProductionExceptionHandler {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  @Override
  public ProductionExceptionHandlerResponse handle(
      ProducerRecord<byte[], byte[]> record, Exception exception) {
    logger.atWarning().withCause(exception).log("Processing error");
    return ProductionExceptionHandlerResponse.CONTINUE;
  }

  @Override
  public void configure(Map<String, ?> map) {}
}
