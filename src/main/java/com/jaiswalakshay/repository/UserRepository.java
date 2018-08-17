package com.jaiswalakshay.repository;

import com.jaiswalakshay.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

//    @Query("SELECT u from user where user.username=:username")
    User findByUsername(@Param("username") String username);
}