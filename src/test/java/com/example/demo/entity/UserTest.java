package com.example.demo.entity;

import com.example.demo.entity.User.GenderEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserTest {

    // Testing Data
    private static final long ID = 1;
    private static final String USERNAME = "Katarina";
    private static final LocalDate BIRTHDATE = LocalDate.parse("2000-05-05");
    private static final String COUNTRY_RESIDENCE = "France";
    private static final String PHONE_NUMBER = "0707070707";
    private static final GenderEnum GENDER = GenderEnum.FEMALE;

    @Test
    @DisplayName("It should create empty userDto")
    public void createEmptyUserDto() {
        // Execute the method call
        User user = new User();

        // Assert the response
        assertThat(user).isNotNull();
    }

    @Test
    @DisplayName("It should create userDto")
    public void createEmptyUser() {
        // Execute the method call
        User user = new User(ID, USERNAME, BIRTHDATE, COUNTRY_RESIDENCE, PHONE_NUMBER, GENDER);

        // Assert the response
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(ID);
        assertThat(user.getUsername()).isEqualTo(USERNAME);
        assertThat(user.getBirthdate()).isEqualTo(BIRTHDATE);
        assertThat(user.getCountryResidence()).isEqualTo(COUNTRY_RESIDENCE);
        assertThat(user.getPhoneNumber()).isEqualTo(PHONE_NUMBER);
        assertThat(user.getGender()).isEqualTo(GENDER);
    }

    @Test
    @DisplayName("It should set attributes to empty userDto")
    public void setAttributesUserDto() {
        // Execute the methods call
        User user = new User();
        user.setId(ID);
        user.setUsername(USERNAME);
        user.setBirthdate(BIRTHDATE);
        user.setCountryResidence(COUNTRY_RESIDENCE);
        user.setPhoneNumber(PHONE_NUMBER);
        user.setGender(GENDER);

        // Assert the response
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(ID);
        assertThat(user.getUsername()).isEqualTo(USERNAME);
        assertThat(user.getBirthdate()).isEqualTo(BIRTHDATE);
        assertThat(user.getCountryResidence()).isEqualTo(COUNTRY_RESIDENCE);
        assertThat(user.getPhoneNumber()).isEqualTo(PHONE_NUMBER);
        assertThat(user.getGender()).isEqualTo(GENDER);
    }

}