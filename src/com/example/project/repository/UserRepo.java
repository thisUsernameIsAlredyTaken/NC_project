package com.example.project.repository;

import com.example.project.entiy.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

    @Query("select u from User u where u.id = :id")
    Optional<User.CoreInfo> findCoreById(@Param("id") String id);

    @Query("select u from User u where u.username = :username")
    Optional<User.CoreInfo> findCoreByUsername(@Param("username") String username);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
