package ru.expanse.user.controller;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import ru.expanse.user.proto.GrpcUserController;
import ru.expanse.user.proto.UserDto;
import ru.expanse.user.proto.UserFilterRequest;
import ru.expanse.user.proto.UserRequest;
import ru.expanse.user.proto.UserResponse;
import ru.expanse.user.service.UserService;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@GrpcService
@RequiredArgsConstructor
public class UserController implements GrpcUserController {
    private final UserService userService;

    @Override
    public Uni<UserResponse> addUser(UserDto request) {
        return userService.addUser(request)
                .map(this::packDto);
    }

    @Override
    public Uni<UserResponse> isValidUser(UserRequest request) {
        return userService.isValidUser(UUID.fromString(request.getId()))
                .map(this::packBoolean);
    }

    @Override
    public Uni<UserResponse> getUserById(UserRequest request) {
        return userService.getUserById(UUID.fromString(request.getId()))
                .map(this::packDto);
    }

    @Override
    public Uni<UserResponse> getFilteredUsers(UserFilterRequest request) {
        return userService.getFilteredUsers(
                        request.getName(),
                        request.getEmail(),
                        new Timestamp(request.getStartBirthDate()),
                        new Timestamp(request.getEndBirthDate()))
                .map(this::packDto);
    }

    @Override
    public Uni<UserResponse> updateUser(UserDto request) {
        return userService.updateUser(request)
                .map(this::packDto);
    }

    @Override
    public Uni<UserResponse> deleteUser(UserRequest request) {
        return userService.deleteUser(UUID.fromString(request.getId()))
                .map(this::packBoolean);
    }

    private UserResponse packDto(UserDto dto) {
        return UserResponse.newBuilder()
                .addUsers(dto)
                .build();
    }

    private UserResponse packDto(List<UserDto> list) {
        return UserResponse.newBuilder()
                .addAllUsers(list)
                .build();
    }

    private UserResponse packBoolean(boolean bool) {
        return UserResponse.newBuilder()
                .setIsSuccessful(bool)
                .build();
    }
}
