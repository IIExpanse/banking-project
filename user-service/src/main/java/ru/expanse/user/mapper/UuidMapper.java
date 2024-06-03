package ru.expanse.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public class UuidMapper {
    public UUID mapToUuid(String uuidString) {
        return uuidString.isEmpty() ? null : UUID.fromString(uuidString);
    }

    public String mapToString(UUID uuid) {
        return uuid == null ? "" : uuid.toString();
    }
}
