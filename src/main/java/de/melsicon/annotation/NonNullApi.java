package de.melsicon.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.annotation.meta.TypeQualifierDefault;

@Target(PACKAGE)
@Retention(CLASS)
@Documented
@javax.annotation.Nonnull
@TypeQualifierDefault({METHOD, PARAMETER})
public @interface NonNullApi {}
