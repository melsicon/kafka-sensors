package de.melsicon.kafka.sensors.serialization.mapping;

import static org.mapstruct.CollectionMappingStrategy.ADDER_PREFERRED;
import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.JSR330;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;
import static org.mapstruct.ReportingPolicy.ERROR;

import org.mapstruct.MapperConfig;

@MapperConfig(
    componentModel = JSR330,
    injectionStrategy = CONSTRUCTOR,
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    nullValuePropertyMappingStrategy = IGNORE,
    collectionMappingStrategy = ADDER_PREFERRED,
    unmappedTargetPolicy = ERROR)
public interface MapStructConfig {}
