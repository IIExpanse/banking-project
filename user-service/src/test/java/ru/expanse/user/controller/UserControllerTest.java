package ru.expanse.user.controller;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.vertx.VertxContextSupport;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.expanse.user.entity.User;
import ru.expanse.user.mapper.UserMapper;
import ru.expanse.user.proto.GrpcUserController;
import ru.expanse.user.proto.UserDto;
import ru.expanse.user.proto.UserFilterRequest;
import ru.expanse.user.proto.UserRequest;
import ru.expanse.user.repository.UserRepository;
import ru.expanse.user.service.UserService;
import ru.expanse.user.util.ObjectFactory;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UserControllerTest {
    @GrpcClient
    GrpcUserController client;
    @Inject
    UserService service;
    @Inject
    UserRepository repository;
    @Inject
    UserMapper mapper;

    @BeforeEach
    void clearDB() throws Throwable {
        VertxContextSupport.subscribeAndAwait(
                () -> Panache.withTransaction(
                        () -> repository.deleteAll()
                )
        );
    }

    @Test
    void addUser() throws Throwable {
        List<UserDto> list = VertxContextSupport.subscribeAndAwait(
                () -> client.addUser(mapper.mapToDto(ObjectFactory.getDefaultUser()))
        ).getUsersList();

        assertEquals(1, list.size());
    }

    @Test
    void getUserById() throws Throwable {
        String id = addDefaultUser().getId();

        List<UserDto> list = doGetUserById(id);

        assertEquals(1, list.size());
    }

    @Test
    void getFilteredUsers() throws Throwable {
        UserDto userDto = addDefaultUser();

        List<UserDto> list = VertxContextSupport.subscribeAndAwait(
                () -> client.getFilteredUsers(UserFilterRequest.newBuilder()
                        .setEndBirthDate(userDto.getBirthDate() - 1)
                        .build())
        ).getUsersList();

        assertTrue(list.isEmpty());

        list = VertxContextSupport.subscribeAndAwait(
                () -> client.getFilteredUsers(UserFilterRequest.newBuilder()
                        .setEndBirthDate(userDto.getBirthDate())
                        .build())
        ).getUsersList();

        assertEquals(1, list.size());
    }

    @Test
    void updateUser() throws Throwable {
        UserDto userDto = addDefaultUser();
        UserDto updatedUser = mapper.mapToDto(User.builder()
                .name("New name")
                .email("New email")
                .phoneNumber(userDto.getPhoneNumber())
                .birthDate(Timestamp.from(Instant.now()))
                .build());

        List<UserDto> list = VertxContextSupport.subscribeAndAwait(
                () -> client.updateUser(updatedUser)
        ).getUsersList();

        UserDto returnedUser = list.get(0);
        assertEquals("New name", returnedUser.getName());
        assertEquals("New email", returnedUser.getEmail());
    }

    @Test
    void deleteUser() throws Throwable {
        String id = addDefaultUser().getId();
        doGetUserById(id);

        assertTrue(doDelete(id));
        assertFalse(doDelete(id));

        StatusRuntimeException e = assertThrows(StatusRuntimeException.class, () -> doGetUserById(id));
        assertEquals(Status.NOT_FOUND, e.getStatus());
    }

    private UserDto addDefaultUser() throws Throwable {
        return addCustomUser(ObjectFactory.getDefaultUser());
    }

    private UserDto addCustomUser(User user) throws Throwable {
        return VertxContextSupport.subscribeAndAwait(
                () -> service.addUser(mapper.mapToDto(user)));
    }

    private List<UserDto> doGetUserById(String id) throws Throwable {
        return VertxContextSupport.subscribeAndAwait(
                () -> client.getUserById(UserRequest.newBuilder()
                        .setId(id)
                        .build())
        ).getUsersList();
    }

    private boolean doDelete(String id) throws Throwable {
        return VertxContextSupport.subscribeAndAwait(
                () -> client.deleteUser(UserRequest.newBuilder()
                        .setId(id)
                        .build())
        ).getIsFound();
    }
}