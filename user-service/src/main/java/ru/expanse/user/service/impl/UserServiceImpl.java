package ru.expanse.user.service.impl;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import ru.expanse.user.dto.UserDto;
import ru.expanse.user.mapper.UserMapper;
import ru.expanse.user.repository.UserRepository;
import ru.expanse.user.service.UserService;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @WithTransaction
    public Uni<UserDto> addUser(UserDto userDto) {
        return userRepository.persist(userMapper.mapToUser(userDto))
                .map(userMapper::mapToDto);
    }

    @Override
    @WithSession
    public Uni<UserDto> getUserById(UUID id) {
        return userRepository.findById(id)
                .onItem().ifNull().failWith(new RuntimeException())
                .map(userMapper::mapToDto);
    }

    @Override
    public Uni<List<UserDto>> getFilteredUsers(String name, String email, Timestamp startBirthDate, Timestamp endBirthDate) {
        return userRepository.getFilteredUsers(name, email, startBirthDate, endBirthDate)
                .map(list -> list.stream().map(userMapper::mapToDto).collect(Collectors.toList()));
    }

    @Override
    @WithTransaction
    public Uni<UserDto> updateUser(UserDto userDto) {
        return userRepository.persist(userMapper.mapToUser(userDto))
                .map(userMapper::mapToDto);
    }

    @Override
    @WithTransaction
    public Uni<Boolean> deleteUser(UUID id) {
        return userRepository.deleteById(id);
    }
}
