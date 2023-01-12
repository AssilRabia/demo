package com.example.demo.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserDataNotValidExceptionTest {

    // Testing data
    private static final String exceptionMessage = "Invalid user data!";

    @Test
    public void createUserDataNotValidException() {
        // Execute the method call
        UserDataNotValidException userDataNotValidException = new UserDataNotValidException(exceptionMessage);

        // Assert the response
        assertThat(userDataNotValidException).hasMessageContaining(exceptionMessage);
    }

}