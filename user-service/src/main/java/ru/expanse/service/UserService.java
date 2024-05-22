package ru.expanse.service;

import io.smallrye.mutiny.Uni;
import ru.expanse.dto.UserDto;

import java.util.UUID;

public interface UserService {
    Uni<UserDto> addUser(UserDto userDto);

    Uni<UserDto> getUserById(UUID id);

    Uni<UserDto> updateUser(UserDto userDto);

    Uni<Boolean> deleteUser(UUID id);
}
