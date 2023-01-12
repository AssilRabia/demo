package com.example.demo.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

/**
 * DateUtils
 *
 * @author Assil Rabia
 */

public class DateUtils {

    private DateUtils() {
    }

    /**
     * Convert Date to LocalDate
     *
     * @param dateToConvert Date
     * @return LocalDate
     */
    public static LocalDate convertDateToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     * Calculate age from Birthdate
     *
     * @param birthDate LocalDate
     * @return int
     */
    public static int calculateAgeFromBirthdate(LocalDate birthDate) {
        LocalDate currentLocalDate = DateUtils.convertDateToLocalDate(new Date());
        return Period.between(birthDate, currentLocalDate).getYears();
    }

}
