package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.UserDataNotValidException;
import com.example.demo.service.UserService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    // Testing data
    private static final long ID = 1;
    private static final UserDto userDto = new UserDto(ID, "Alex", LocalDate.parse("2000-01-01"), "France", "0606060606", "MALE");

    @Test
    @DisplayName("POST /users - Success")
    public void saveUser() throws Exception {
        // Set up mock service and post request
        doReturn(userDto.getId()).when(userService).saveUser(userDto);

        ObjectMapper mapper = (new ObjectMapper()).registerModule(new JavaTimeModule());
        RequestBuilder postRequestBuilder = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userDto));

        // Execute the controller call
        mockMvc.perform(postRequestBuilder)

                // Validate the response code and content type
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$", Matchers.is((new Long(ID)).intValue())));
    }

    @Test
    @DisplayName("POST /users - Failed")
    public void saveUserFailed() throws Exception {
        // Set up testing data, mock service and post request
        String requestType = "POST";
        String requestPath = "/users";
        String exceptionMessage = "Invalid username!";
        UserDataNotValidException exception = new UserDataNotValidException(exceptionMessage);
        doThrow(exception).when(userService).saveUser(any());

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
    @DisplayName("GET /users/{id} - Success")
    public void getUserById() throws Exception {
        // Set up mock service
        doReturn(userDto).when(userService).findUserById(ID);

        // Execute the controller call
        mockMvc.perform(get("/users/{id}", ID))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.username", Matchers.is(userDto.getUsername())))
                .andExpect(jsonPath("$.birthdate", Matchers.is(userDto.getBirthdate().toString())))
                .andExpect(jsonPath("$.countryResidence", Matchers.is(userDto.getCountryResidence())))
                .andExpect(jsonPath("$.phoneNumber", Matchers.is(userDto.getPhoneNumber())))
                .andExpect(jsonPath("$.gender", Matchers.is(userDto.getGender())));
    }

    @Test
    @DisplayName("GET /users/{id} - Failed")
    public void getUserByIdNotFound() throws Exception {
        // Set up testing data and mock service
        String requestType = "GET";
        String requestPath = "/users/" + ID;
        String exceptionMessage = "There is no user found with such id: " + ID + " !";
        NotFoundException exception = new NotFoundException(exceptionMessage);
        doThrow(exception).when(userService).findUserById(ID);

        // Execute the controller call
        mockMvc.perform(get("/users/{id}", ID))

                // Validate the response code and content type
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.errorCode", Matchers.is(HttpStatus.NOT_FOUND.toString())))
                .andExpect(jsonPath("$.errorMessage", Matchers.is(exceptionMessage)))
                .andExpect(jsonPath("$.requestType", Matchers.is(requestType)))
                .andExpect(jsonPath("$.requestPath", Matchers.is(requestPath)));
    }

}