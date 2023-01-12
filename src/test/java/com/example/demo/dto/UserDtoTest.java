package com.example.demo.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserDtoTest {

    // Testing Data
    private static final long ID = 1;
    private static final String USERNAME = "Alex";
    private static final LocalDate BIRTHDATE = LocalDate.parse("2000-01-01");
    private static final String COUNTRY_RESIDENCE = "France";
    private static final String PHONE_NUMBER = "33606060606";
    private static final String GENDER = "male";

    @Test
    @DisplayName("It should create empty userDto")
    public void createEmptyUserDto() {
        // Execute the method call
        UserDto userDto = new UserDto();

        // Assert the response
        assertThat(userDto).isNotNull();
    }

    @Test
    @DisplayName("It should create userDto")
    public void createEmptyUser() {
        // Execute the method call
        UserDto userDto = new UserDto(ID, USERNAME, BIRTHDATE, COUNTRY_RESIDENCE, PHONE_NUMBER, GENDER);

        // Assert the response
        assertThat(userDto).isNotNull();
        assertThat(userDto.getId()).isEqualTo(ID);
        assertThat(userDto.getUsername()).isEqualTo(USERNAME);
        assertThat(userDto.getBirthdate()).isEqualTo(BIRTHDATE);
        assertThat(userDto.getCountryResidence()).isEqualTo(COUNTRY_RESIDENCE);
        assertThat(userDto.getPhoneNumber()).isEqualTo(PHONE_NUMBER);
        assertThat(userDto.getGender()).isEqualTo(GENDER);
    }

    @Test
    @DisplayName("It should set attributes to empty userDto")
    public void setAttributesUserDto() {
        // Execute the methods call
        UserDto userDto = new UserDto();
        userDto.setId(ID);
        userDto.setUsername(USERNAME);
        userDto.setBirthdate(BIRTHDATE);
        userDto.setCountryResidence(COUNTRY_RESIDENCE);
        userDto.setPhoneNumber(PHONE_NUMBER);
        userDto.setGender(GENDER);

        // Assert the response
        assertThat(userDto).isNotNull();
        assertThat(userDto.getId()).isEqualTo(ID);
        assertThat(userDto.getUsername()).isEqualTo(USERNAME);
        assertThat(userDto.getBirthdate()).isEqualTo(BIRTHDATE);
        assertThat(userDto.getCountryResidence()).isEqualTo(COUNTRY_RESIDENCE);
        assertThat(userDto.getPhoneNumber()).isEqualTo(PHONE_NUMBER);
        assertThat(userDto.getGender()).isEqualTo(GENDER);
    }

}