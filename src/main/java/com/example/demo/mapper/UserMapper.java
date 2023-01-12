package com.example.demo.mapper;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.entity.User.GenderEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.util.StringUtils;

/**
 * UserMapper
 *
 * @author Assil Rabia
 */

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Map userDto to user
     *
     * @param userDto UserDto
     * @return User
     */
    @Mapping(target = "gender", expression = "java(mapGenderEnumFromString(userDto.getGender()))")
    User toEntity(UserDto userDto);

    /**
     * Map user to userDto
     *
     * @param user User
     * @return UserDto
     */
    UserDto toDto(User user);

    /**
     * Map a string to GenderEnum
     *
     * @param gender string
     * @return GenderEnum
     */
    default GenderEnum mapGenderEnumFromString(String gender) {
        if (!StringUtils.hasLength(gender))
            return null;

        if ("male".equalsIgnoreCase(gender))
            return User.GenderEnum.MALE;

        if ("female".equalsIgnoreCase(gender))
            return User.GenderEnum.FEMALE;

        return User.GenderEnum.OTHER;
    }

}
