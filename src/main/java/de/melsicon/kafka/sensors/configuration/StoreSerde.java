package de.melsicon.kafka.sensors.configuration;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

@Target({METHOD, PARAMETER, FIELD})
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface StoreSerde {}
