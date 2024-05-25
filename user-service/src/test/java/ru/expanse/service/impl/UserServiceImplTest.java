package ru.expanse.service.impl;

import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import ru.expanse.entity.User;
import ru.expanse.mapper.UserMapper;
import ru.expanse.repository.UserRepository;
import ru.expanse.utils.ObjectFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    UserRepository userRepository;
    UserServiceImpl userService;
    UserMapper userMapper;

    @BeforeEach
    void refresh() {
        userRepository = Mockito.mock(UserRepository.class);
        userMapper = Mappers.getMapper(UserMapper.class);
        userService = new UserServiceImpl(userRepository, userMapper);
    }

    @Test
    void addUser() throws Exception {
        User passedUser = ObjectFactory.getDefaultUser();
        User returnedUser = ObjectFactory.getDefaultUser();
        returnedUser.setId(UUID.randomUUID());

        Mockito.when(userRepository.persist(ArgumentMatchers.any(User.class)))
                .thenReturn(Uni.createFrom().item(returnedUser));

        UUID id = userService.addUser(userMapper.mapToDto(passedUser)).subscribe().asCompletionStage().get().id();
        assertEquals(returnedUser.getId(), id);
    }

    @Test
    void getUserById() throws Exception {
        User returnedUser = ObjectFactory.getDefaultUser();
        returnedUser.setId(UUID.randomUUID());

        Mockito.when(userRepository.findById(returnedUser.getId()))
                .thenReturn(Uni.createFrom().item(returnedUser));

        UUID id = userService.getUserById(returnedUser.getId()).subscribe().asCompletionStage().get().id();

        assertEquals(returnedUser.getId(), id);
    }

    @Test
    void updateUser() {

    }

    @Test
    void deleteUser() {
    }
}