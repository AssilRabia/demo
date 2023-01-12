package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * UserDto
 *
 * @author Assil Rabia
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @Schema(description = "The id well be auto-generated when saved in database")
    private Long id;

    @Schema(required = true,
            example = "Alex")
    private String username;

    @Schema(description = "The birthdate must be in format yyyy-mm-dd",
            required = true,
            example = "2000-01-01")
    private LocalDate birthdate;

    @Schema(required = true,
            example = "France")
    private String countryResidence;

    @Schema(description = "The phone number must be a valid French phone number",
            example = "+33606060606")
    private String phoneNumber;

    @Schema(description = "Gender must be Male, Female or Other",
            example = "Male")
    private String gender;

}
