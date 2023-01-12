package com.example.demo.service.impl;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.UserDataNotValidException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * UserServiceImpl
 *
 * @author Assil Rabia
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private static final int MIN_AGE = 18;
    private static final String RESIDENCE_COUNTRY_ALLOWED = "France";
    private static final String FRANCE_PHONE_NUMBER_PATTERN = "(0|\\+33|0033)(6|7)[0-9]{8}";

    /**
     * save user
     *
     * @param userDto UserDto
     * @return long
     */
    @Override
    public long saveUser(UserDto userDto) {
        validateUserData(userDto);
        return userRepository.save(UserMapper.INSTANCE.toEntity(userDto)).getId();
    }

    /**
     * Find user by id
     *
     * @param id long
     * @return UserDto
     */
    @Override
    public UserDto findUserById(long id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new NotFoundException("There is no user found with such id: " + id + " !");
        }

        return UserMapper.INSTANCE.toDto(user);
    }

    /**
     * Validate the user data
     *
     * @param userDto UserDto
     */
    private void validateUserData(UserDto userDto) {
        if (!StringUtils.hasLength(userDto.getUsername())) {
            throw new UserDataNotValidException("Invalid username! it must not be null or empty.");
        }

        if (userDto.getBirthdate() == null || userDto.getBirthdate().compareTo(DateUtils.convertDateToLocalDate(new Date())) > 0) {
            throw new UserDataNotValidException("Invalid birthDate! it must not be null or empty.");
        }

        if (!StringUtils.hasLength(userDto.getCountryResidence())) {
            throw new UserDataNotValidException("Invalid country of residence! it must not be null or empty.");
        }

        if (userDto.getPhoneNumber() != null && !userDto.getPhoneNumber().matches(FRANCE_PHONE_NUMBER_PATTERN)) {
            throw new UserDataNotValidException("Invalid phone number! It must be a French phone number.");
        }

        int userAge = DateUtils.calculateAgeFromBirthdate(userDto.getBirthdate());
        if (userAge < MIN_AGE || !RESIDENCE_COUNTRY_ALLOWED.equalsIgnoreCase(userDto.getCountryResidence())) {
            throw new UserDataNotValidException("Account creation is only allowed for adult French residents!");
        }
    }

}
