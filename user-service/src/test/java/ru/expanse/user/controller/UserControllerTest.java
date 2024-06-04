package ru.expanse.user.controller;

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
import ru.expanse.user.repository.UserRepository;
import ru.expanse.user.service.UserService;
import ru.expanse.user.util.ObjectFactory;

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
    void addUser() {
    }

    @Test
    void getUserById() {
    }

    @Test
    void getFilteredUsers() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    private UserDto addDefaultUser() throws Throwable {
        return addCustomUser(ObjectFactory.getDefaultUser());
    }

    private UserDto addCustomUser(User user) throws Throwable {
        return VertxContextSupport.subscribeAndAwait(
                () -> service.addUser(mapper.mapToDto(user)));
    }
}