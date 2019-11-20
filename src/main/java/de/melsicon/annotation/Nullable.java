package de.melsicon.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.annotation.meta.TypeQualifierNickname;

@Target({METHOD, PARAMETER, FIELD, LOCAL_VARIABLE, TYPE_USE})
@Retention(CLASS)
@Documented
@javax.annotation.Nullable
@TypeQualifierNickname
public @interface Nullable {}
