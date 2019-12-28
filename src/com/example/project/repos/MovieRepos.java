package com.example.project.repos;


import com.example.project.model.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepos extends JpaRepository<Movie, String> {

    int MAX_ID_LENGTH = 11;

    default int getMaxIdLength() {
        return MAX_ID_LENGTH;
    }

    List<Movie> findByTitleContainingIgnoreCase(String pattern, Pageable pageable);
}
