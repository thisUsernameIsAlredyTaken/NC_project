package com.example.project.repository;

import com.example.project.entiy.PlannedMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlannedMovieRepo extends JpaRepository<PlannedMovie, Long> {

    @Query("select pm from PlannedMovie pm where pm.user.id = :id")
    List<PlannedMovie.NoUser> findByUserId(@Param("id") String userId);

    @Query("select pm from PlannedMovie pm where pm.user.username = :name")
    List<PlannedMovie.NoUser> findByUsername(@Param("name") String username);
}
