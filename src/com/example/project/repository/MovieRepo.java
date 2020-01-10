package com.example.project.repository;

import com.example.project.entiy.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepo extends JpaRepository<Movie, String> {

    @Query("select m from Movie m where lower(m.title) like :pattern")
    List<Movie> search(@Param("pattern") String pattern, Pageable pageable);

    List<Movie> findByTitleContainsIgnoreCase(String pattern, Pageable pageable);
}
