package com.example.demo.exception;

import com.example.demo.controller.UserController;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.GlobalExceptionHandler.APIError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
class GlobalExceptionHandlerTest {

    @MockBean
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    @DisplayName("It should handel NotFoundException response")
    public void handelNotFoundException() throws Exception {
        // Set up testing data and mock controller
        long id = 1;
        String requestType = "GET";
        String requestPath = "/users/" + id;
        String exceptionMessage = "There is no user found with such id: " + id + " !";
        NotFoundException exception = new NotFoundException(exceptionMessage);
        MockHttpServletRequest request = new MockHttpServletRequest(requestType, requestPath);

        doReturn(globalExceptionHandler.handelNotFoundException(exception, request))
                .when(userController)
                .getUserById(id);

        // Execute the controller call
        mockMvc.perform(get("/users/{id}", 1))

                // Validate the response code and content type
                .andExpect(status().isNotFound())

                // Validate the returned fields
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.errorCode", Matchers.is(HttpStatus.NOT_FOUND.toString())))
                .andExpect(jsonPath("$.errorMessage", Matchers.is(exceptionMessage)))
                .andExpect(jsonPath("$.requestType", Matchers.is(requestType)))
                .andExpect(jsonPath("$.requestPath", Matchers.is(requestPath)));
    }

    @Test
    @DisplayName("It should handel UserDataNotValidException response")
    public void handelUserDataNotValidException() throws Exception {
        // Set up testing data and mock controller
        String requestType = "POST";
        String requestPath = "/users";
        String exceptionMessage = "Invalid username!";
        UserDataNotValidException exception = new UserDataNotValidException(exceptionMessage);
        MockHttpServletRequest request = new MockHttpServletRequest(requestType, requestPath);
        UserDto userDto = new UserDto();

        doReturn(globalExceptionHandler.handelUserDataNotValidException(exception, request))
                .when(userController)
                .saveUser(new UserDto());

        ObjectMapper mapper = (new ObjectMapper()).registerModule(new JavaTimeModule());
        RequestBuilder postRequestBuilder = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userDto));

        // Execute the controller call
        mockMvc.perform(postRequestBuilder)

                // Validate the response code and content type
                .andExpect(status().isBadRequest())

                // Validate the returned fields
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.errorCode", Matchers.is(HttpStatus.BAD_REQUEST.toString())))
                .andExpect(jsonPath("$.errorMessage", Matchers.is(exceptionMessage)))
                .andExpect(jsonPath("$.requestType", Matchers.is(requestType)))
                .andExpect(jsonPath("$.requestPath", Matchers.is(requestPath)));
    }

    @Test
    @DisplayName("It should set attributes to empty APIError")
    public void setAttributesAPIError() {
        // Set up Testing data
        LocalDateTime timestamp = LocalDateTime.now();
        String errorCode = HttpStatus.NOT_FOUND.toString();
        String errorMessage = "There is no user found with such id: 1000 !";
        String requestType = "GET";
        String requestPath = "/users/1000";
        APIError apiError = APIError.builder().build();

        // Execute the methods call
        apiError.setTimestamp(timestamp);
        apiError.setErrorCode(errorCode);
        apiError.setErrorMessage(errorMessage);
        apiError.setRequestType(requestType);
        apiError.setRequestPath(requestPath);

        //Assert the responses
        assertThat(apiError).isNotNull();
        assertThat(apiError.getTimestamp()).isEqualTo(timestamp);
        assertThat(apiError.getErrorCode()).isEqualTo(errorCode);
        assertThat(apiError.getErrorMessage()).isEqualTo(errorMessage);
        assertThat(apiError.getRequestType()).isEqualTo(requestType);
        assertThat(apiError.getRequestPath()).isEqualTo(requestPath);
    }

}