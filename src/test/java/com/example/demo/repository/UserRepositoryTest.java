package com.example.demo.repository;

import com.example.demo.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("It should get user by id")
    public void findUserById() {
        // Set up testing data
        User user = new User(null, "Alex", LocalDate.parse("2000-01-01"), "France", null, null);

        // Execute the repository call
        User userAfterSaving = userRepository.save(user);
        User userFoundWithId = userRepository.findUserById(userAfterSaving.getId());

        // Assert the response
        assertThat(userFoundWithId).isEqualTo(userAfterSaving);
    }

    @Test
    @DisplayName("It should not find user when a user with such id does not exist")
    public void canNotFindUserById() {
        // Setup testing data
        long id = 100;

        // Execute the repository call
        // Assert the response
        assertThat(userRepository.findUserById(id)).isNull();
    }

}
