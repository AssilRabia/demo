package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.UserDataNotValidException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepositoryMock);
    }

    @Test
    @DisplayName("It should add user")
    public void saveUser() {
        // Set up testing data and mock repository
        UserDto user1 = createUser("Alex", LocalDate.parse("2000-01-01"), "France", null, null);
        long id1 = 1;
        User user1AfterSaving = new User(id1, "Alex", LocalDate.parse("2000-01-01"), "France", null, null);
        doReturn(user1AfterSaving).when(userRepositoryMock).save(UserMapper.INSTANCE.toEntity(user1));

        UserDto user2 = createUser("Katarina", LocalDate.parse("2000-01-01"), "France", "0707070707", "female");
        long id2 = 2;
        User user2AfterSaving = new User(id2, "Katarina", LocalDate.parse("2000-01-01"), "France", "0707070707", User.GenderEnum.FEMALE);
        doReturn(user2AfterSaving).when(userRepositoryMock).save(UserMapper.INSTANCE.toEntity(user2));

        // Execute the service call
        long idUser1 = userService.saveUser(user1);
        long idUser2 = userService.saveUser(user2);

        // Assert the response
        assertThat(idUser1).isEqualTo(id1);
        assertThat(idUser2).isEqualTo(id2);
    }

    @Test
    @DisplayName("It should throw UserDataNotValidException when user data is invalid")
    public void canNotSaveUser() {
        // Set up testing Data
        UserDto user1 = new UserDto();
        UserDto user2 = createUser("Alex", null, null, null, null);
        UserDto user3 = createUser("Alex", LocalDate.parse("2024-01-01"), null, null, null);
        UserDto user4 = createUser("Alex", LocalDate.parse("2000-01-01"), null, null, null);
        UserDto user5 = createUser("Alex", LocalDate.parse("2000-01-01"), "France", "+336060606060", null);
        UserDto user6 = createUser("Alex", LocalDate.parse("2022-01-01"), "France", null, null);
        UserDto user7 = createUser("Alex", LocalDate.parse("2000-01-01"), "Suisse", null, null);

        // Execute the service call
        // Assert the response
        assertThatThrownBy(() -> userService.saveUser(user1))
                .isInstanceOf(UserDataNotValidException.class)
                .hasMessageContaining("Invalid username! it must not be null or empty.");
        verify(userRepositoryMock, never()).save(any());

        assertThatThrownBy(() -> userService.saveUser(user2))
                .isInstanceOf(UserDataNotValidException.class)
                .hasMessageContaining("Invalid birthDate! it must not be null or empty.");
        verify(userRepositoryMock, never()).save(any());

        assertThatThrownBy(() -> userService.saveUser(user3))
                .isInstanceOf(UserDataNotValidException.class)
                .hasMessageContaining("Invalid birthDate! it must not be null or empty.");
        verify(userRepositoryMock, never()).save(any());

        assertThatThrownBy(() -> userService.saveUser(user4))
                .isInstanceOf(UserDataNotValidException.class)
                .hasMessageContaining("Invalid country of residence! it must not be null or empty.");
        verify(userRepositoryMock, never()).save(any());

        assertThatThrownBy(() -> userService.saveUser(user5))
                .isInstanceOf(UserDataNotValidException.class)
                .hasMessageContaining("Invalid phone number! It must be a French phone number.");
        verify(userRepositoryMock, never()).save(any());

        assertThatThrownBy(() -> userService.saveUser(user6))
                .isInstanceOf(UserDataNotValidException.class)
                .hasMessageContaining("Account creation is only allowed for adult French residents!");
        verify(userRepositoryMock, never()).save(any());

        assertThatThrownBy(() -> userService.saveUser(user7))
                .isInstanceOf(UserDataNotValidException.class)
                .hasMessageContaining("Account creation is only allowed for adult French residents!");
        verify(userRepositoryMock, never()).save(any());
    }

    @Test
    @DisplayName("It should return user by id")
    public void findUserById() {
        // Set up testing data and mock repository
        long id = 1;
        User user = new User(id, "Qlex", LocalDate.parse("2000-01-01"), "France", null, null);
        doReturn(user).when(userRepositoryMock).findUserById(id);

        // Execute the service call
        UserDto result = userService.findUserById(id);

        // Assert the response
        assertThat(result).isNotNull();
        assertThat(UserMapper.INSTANCE.toEntity(result)).isEqualTo(user);
    }

    @Test
    @DisplayName("It should throw NotFoundException when user with such id doesn't exist")
    public void canNotFindUserById() {
        // Set up testing data
        long id = 0;

        // Execute the service call
        // Assert the response
        assertThatThrownBy(() -> userService.findUserById(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("There is no user found with such id: " + id + " !");
    }

    private UserDto createUser(String name, LocalDate birthdate, String countryResidence, String phoneNumber, String gender) {
        return new UserDto(null, name, birthdate, countryResidence, phoneNumber, gender);
    }

}
