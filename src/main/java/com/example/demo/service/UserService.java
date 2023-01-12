package com.example.demo.service;

import com.example.demo.dto.UserDto;

/**
 * UserService
 *
 * @author Assil Rabia
 */

public interface UserService {

    /**
     * save user
     *
     * @param userDto UserDto
     * @return long
     */
    long saveUser(UserDto userDto);

    /**
     * Find user by id
     *
     * @param id long
     * @return UserDto
     */
    UserDto findUserById(long id);

}
