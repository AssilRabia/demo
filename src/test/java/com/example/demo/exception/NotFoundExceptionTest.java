package com.example.demo.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class NotFoundExceptionTest {

    // Testing data
    private static final String exceptionMessage = "User not found";

    @Test
    public void createNotFoundException() {
        // Execute the method call
        NotFoundException notFoundException = new NotFoundException(exceptionMessage);

        // Assert the response
        assertThat(notFoundException).hasMessageContaining(exceptionMessage);
    }

}