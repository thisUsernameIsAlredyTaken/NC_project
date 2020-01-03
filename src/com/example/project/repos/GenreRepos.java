package com.example.project.repos;

import com.example.project.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepos extends JpaRepository<Genre, Integer> {
}
