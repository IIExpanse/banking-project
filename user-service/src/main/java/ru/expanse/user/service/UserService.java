package ru.expanse.user.service;

import io.smallrye.mutiny.Uni;
import ru.expanse.user.proto.UserDto;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface UserService {
    Uni<UserDto> addUser(UserDto userDto);

    Uni<UserDto> getUserById(UUID id);

    Uni<Boolean> isValidUser(UUID id);

    Uni<List<UserDto>> getFilteredUsers(String name,
                                        String email,
                                        Timestamp startBirthDate,
                                        Timestamp endBirthDate);

    Uni<UserDto> updateUser(UserDto userDto);

    Uni<Boolean> deleteUser(UUID id);
}
