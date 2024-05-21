package ru.expanse.dto;

import java.util.UUID;

public record UserDto(UUID id, UUID accountId, String name, String email, String phoneNumber) {
}
