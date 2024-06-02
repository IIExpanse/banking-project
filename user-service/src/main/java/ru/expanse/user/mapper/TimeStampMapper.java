package ru.expanse.user.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.sql.Timestamp;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public class TimeStampMapper {
    public long convertToMillis(Timestamp timestamp) {
        return timestamp.getTime();
    }

    public Timestamp convertToTimestamp(long millis) {
        return new Timestamp(millis);
    }
}
