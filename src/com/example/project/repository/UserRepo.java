package com.example.project.repository;

import com.example.project.entiy.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {

    @Query("select u from User u where u.id = :id")
    Optional<User.CoreInfo> findCoreById(@Param("id") String id);
}
