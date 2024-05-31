package ru.expanse.user.dto;

import java.sql.Timestamp;
import java.util.UUID;

public record UserDto(
        UUID id,
        UUID accountId,
        String name,
        String email,
        String phoneNumber,
        Timestamp birthDate) {
}
