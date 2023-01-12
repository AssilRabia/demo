package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * UserRepository
 *
 * @author Assil Rabia
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by id
     *
     * @param id long
     * @return User
     */
    @Query(value = "SELECT u FROM User u WHERE u.id = :id")
    User findUserById(@Param("id") long id);

}
