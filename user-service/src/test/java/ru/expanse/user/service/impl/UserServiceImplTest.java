package ru.expanse.user.service.impl;

import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import ru.expanse.user.entity.User;
import ru.expanse.user.mapper.TimeStampMapper;
import ru.expanse.user.mapper.UserMapper;
import ru.expanse.user.mapper.UuidMapper;
import ru.expanse.user.proto.UserDto;
import ru.expanse.user.repository.UserRepository;
import ru.expanse.user.util.ObjectFactory;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    UserRepository userRepository;
    UserServiceImpl userService;
    UserMapper userMapper;

    @BeforeEach
    void refresh() throws Exception {
        userRepository = Mockito.mock(UserRepository.class);
        userMapper = Mappers.getMapper(UserMapper.class);
        userService = new UserServiceImpl(userRepository, userMapper);
        setMappers();
    }

    @Test
    void addUser() throws Exception {
        User passedUser = ObjectFactory.getDefaultUser();
        User returnedUser = ObjectFactory.getDefaultUser();
        returnedUser.setId(UUID.randomUUID());

        Mockito.when(userRepository.persist(ArgumentMatchers.any(User.class)))
                .thenReturn(Uni.createFrom().item(returnedUser));

        UUID id = UUID.fromString(userService.addUser(userMapper.mapToDto(passedUser)).subscribe().asCompletionStage().get().getId());
        assertEquals(returnedUser.getId(), id);
    }

    @Test
    void isValidUser() throws Throwable {
        User returnedUser = ObjectFactory.getDefaultUser();
        returnedUser.setId(UUID.randomUUID());

        Mockito.when(userRepository.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Uni.createFrom().item(returnedUser));

        boolean answer = isValidUser(returnedUser.getId());
        assertTrue(answer);

        returnedUser.setIsBlocked(true);
        answer = isValidUser(returnedUser.getId());
        assertFalse(answer);

        Mockito.when(userRepository.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Uni.createFrom().nullItem());

        answer = isValidUser(returnedUser.getId());
        assertFalse(answer);
    }

    @Test
    void getUserById() throws Exception {
        User returnedUser = ObjectFactory.getDefaultUser();
        returnedUser.setId(UUID.randomUUID());

        Mockito.when(userRepository.findById(returnedUser.getId()))
                .thenReturn(Uni.createFrom().item(returnedUser));

        UUID id = UUID.fromString(userService.getUserById(returnedUser.getId()).subscribe().asCompletionStage().get().getId());

        assertEquals(returnedUser.getId(), id);
    }

    @Test
    void getFilteredUsers() throws Exception {
        User returnedUser = ObjectFactory.getDefaultUser();
        Mockito.when(userRepository.getFilteredUsers(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(Timestamp.class),
                        ArgumentMatchers.any(Timestamp.class)))
                .thenReturn(Uni.createFrom().item(List.of(returnedUser)));

        List<UserDto> list = userService.getFilteredUsers(
                "abc",
                "abc",
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.now())).subscribeAsCompletionStage().get();

        assertEquals(1, list.size());
    }

    @Test
    void updateUser() throws Exception {
        User returnedUser = ObjectFactory.getDefaultUser();
        returnedUser.setId(UUID.randomUUID());

        Mockito.when(userRepository.persist(ArgumentMatchers.any(User.class)))
                .thenReturn(Uni.createFrom().item(returnedUser));

        UUID id = UUID.fromString(userService.updateUser(userMapper.mapToDto(returnedUser))
                .subscribe().asCompletionStage().get().getId());

        assertEquals(returnedUser.getId(), id);
    }

    @Test
    void deleteUser() throws Exception {
        Mockito.when(userRepository.deleteById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Uni.createFrom().item(true));
        assertTrue(userService.deleteUser(UUID.randomUUID()).subscribe().asCompletionStage().get());
    }

    private void setMappers() throws Exception {
        setMapperForField("timeStampMapper", TimeStampMapper.class);
        setMapperForField("uuidMapper", UuidMapper.class);
    }

    private void setMapperForField(String fieldName, Class<?> mapperClass) throws Exception {
        Field f = userMapper.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(userMapper, Mappers.getMapper(mapperClass));
    }

    private boolean isValidUser(UUID id) throws Throwable {
        return userService.isValidUser(id).subscribeAsCompletionStage().get();
    }
}