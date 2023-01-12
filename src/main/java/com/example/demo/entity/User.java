package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * User
 *
 * @author Assil Rabia
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "myapp_user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name = "countryResidence", nullable = false)
    private String countryResidence;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    public enum GenderEnum {
        MALE, FEMALE, OTHER
    }

}
