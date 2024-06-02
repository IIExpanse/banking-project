package ru.expanse.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public class UuidMapper {
    public UUID mapToUuid(String uuid) {
        return UUID.fromString(uuid);
    }

    public String mapToString(UUID uuid) {
        return uuid.toString();
    }
}
