package com.example.project.repos;


import com.example.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepos extends JpaRepository<User, String> {

    int MAX_ID_LENGTH = 11;

    default int getMaxIdLength() {
        return MAX_ID_LENGTH;
    }

    Optional<User> findByUsernameEquals(String username);
}
