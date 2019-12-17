package com.example.project.repos;

import com.example.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepos extends JpaRepository<User, String> {
}
