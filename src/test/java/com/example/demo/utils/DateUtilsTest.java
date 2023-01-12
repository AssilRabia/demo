package com.example.demo.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DateUtilsTest {

    @Test
    @DisplayName("It should convert date to localDate")
    public void convertDateToLocalDate() throws Exception {
        // Set up testing data
        String stringDate = "2000-01-01";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
        LocalDate localDate = LocalDate.parse(stringDate);

        // Execute the method call
        LocalDate localDateAfterConvert = DateUtils.convertDateToLocalDate(date);

        // Assert the response
        assertThat(localDateAfterConvert).isEqualTo(localDate);
    }

    @Test
    @DisplayName("It should calculate age from localDate")
    public void calculateAgeFromBirthDate() {
        // Set up testing data
        int age = 23;
        LocalDate localDate = LocalDate.parse("2000-01-01");

        // Execute the method call
        int calculatedAge = DateUtils.calculateAgeFromBirthdate(localDate);

        // Assert the response
        assertThat(calculatedAge).isEqualTo(age);
    }

}
