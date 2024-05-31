package ru.expanse.user.mapper;


import java.sql.Timestamp;

public class TimeStampMapper {
    public long convertToMillis(Timestamp timestamp) {
        return timestamp.getTime();
    }
    public Timestamp convertToTimestamp(long millis) {
        return new Timestamp(millis);
    }
}
