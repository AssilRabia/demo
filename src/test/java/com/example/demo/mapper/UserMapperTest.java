package com.example.demo.mapper;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.entity.User.GenderEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserMapperTest {

    // Testing data
    private static final User user = new User(1L, "Alex", LocalDate.parse("2000-01-01"), "France", "0606060606", GenderEnum.MALE);
    private static final UserDto userDto = new UserDto(1L, "Alex", LocalDate.parse("2000-01-01"), "France", "0606060606", "MALE");

    @Test
    @DisplayName("It should map userDto to user")
    public void toEntity() {
        // Execute the method call
        User mappedUser = UserMapper.INSTANCE.toEntity(userDto);
        User nullUser = UserMapper.INSTANCE.toEntity(null);

        // Assert the response
        assertThat(mappedUser).isEqualTo(user);
        assertThat(nullUser).isNull();
    }

    @Test
    @DisplayName("It should map user to userDto")
    public void toDto() {
        // Execute the method call
        UserDto mappedUserDto = UserMapper.INSTANCE.toDto(user);
        UserDto nullUserDto = UserMapper.INSTANCE.toDto(null);

        // Assert the response
        assertThat(mappedUserDto).isEqualTo(userDto);
        assertThat(nullUserDto).isNull();
    }

    @Test
    @DisplayName("It should map GenderEnum from sting")
    public void mapGenderEnumFromString() {
        // Set up testing data
        String male = "male";
        String female = "Female";
        String other = "Other";

        // Execute the method call
        // Assert the response
        assertThat(UserMapper.INSTANCE.mapGenderEnumFromString(null)).isNull();
        assertThat(UserMapper.INSTANCE.mapGenderEnumFromString(male)).isEqualTo(GenderEnum.MALE);
        assertThat(UserMapper.INSTANCE.mapGenderEnumFromString(female)).isEqualTo(GenderEnum.FEMALE);
        assertThat(UserMapper.INSTANCE.mapGenderEnumFromString(other)).isEqualTo(GenderEnum.OTHER);
    }

}