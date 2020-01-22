package com.example.project.repository;

import com.example.project.entiy.WatchedMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchedMovieRepo extends JpaRepository<WatchedMovie, Long> {

    @Query("select wm from WatchedMovie wm where wm.user.id = :id")
    List<WatchedMovie.NoUser> findByUserId(@Param("id") String userId);

    @Query("select wm from WatchedMovie wm where wm.user.username = :name")
    List<WatchedMovie.NoUser> findByUsername(@Param("name") String username);

    @Query("select count(wm) from WatchedMovie wm where wm.user.id = :id")
    int countById(@Param("id") String userId);

    @Query("select count(wm) from WatchedMovie wm where wm.user.username = :name")
    int countByUsername(@Param("name") String username);
}
