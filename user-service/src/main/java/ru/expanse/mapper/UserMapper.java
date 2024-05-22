package ru.expanse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.expanse.dto.UserDto;
import ru.expanse.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface UserMapper {
    UserDto mapToDto(User user);

    User mapToUser(UserDto userDto);
}
