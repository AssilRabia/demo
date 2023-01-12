package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.GlobalExceptionHandler.APIError;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * UserController
 *
 * @author Assil Rabia
 */

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Save user
     *
     * @param userDto UserDto
     * @return ResponseEntity
     */
    @PostMapping
    @Operation(summary = "Create a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class, example = "1"))}),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid user data!", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = APIError.class))}),
            @ApiResponse(responseCode = "500", description = "Server error occurred!", content = @Content)
    })
    public ResponseEntity<Long> saveUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.saveUser(userDto), HttpStatus.CREATED);
    }

    /**
     * Find user by id
     *
     * @param id long
     * @return ResponseEntity
     */
    @GetMapping("{id}")
    @Operation(summary = "Find a user by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "404", description = "No user found!", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = APIError.class))}),
            @ApiResponse(responseCode = "500", description = "Server error occurred!", content = @Content)
    })
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") long id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

}
