package ru.expanse.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.expanse.user.dto.UserDto;
import ru.expanse.user.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface UserMapper {
    UserDto mapToDto(User user);

    User mapToUser(UserDto userDto);
}
