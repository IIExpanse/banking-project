package ru.expanse.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.expanse.user.entity.User;
import ru.expanse.user.proto.UserDto;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI,
        uses = {TimeStampMapper.class, UuidMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
    UserDto mapToDto(User user);

    User mapToUser(UserDto userDto);
}
