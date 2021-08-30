/** */
@CheckReturnValue
@DefaultQualifier(
    value = NonNull.class,
    locations = {TypeUseLocation.PARAMETER, TypeUseLocation.RETURN})
@Gson.TypeAdapters
package de.melsicon.kafka.sensors.type.gson;

import com.google.errorprone.annotations.CheckReturnValue;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.checkerframework.framework.qual.TypeUseLocation;
import org.immutables.gson.Gson;
