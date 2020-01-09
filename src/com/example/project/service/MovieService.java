package com.example.project.service;

import com.example.project.entiy.Movie;
import com.example.project.entiy.User;
import com.example.project.repository.MovieRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepo movieRepo;

    public boolean add(Movie movie) {
        if (movie == null || movieRepo.existsById(movie.getId())) {
            return false;
        }
        movieRepo.save(movie);
        return false;
    }

    public Movie findById(String id) {
        return movieRepo.findById(id).orElse(null);
    }

    public boolean updateById(String id, Movie movie) {
        if (id == null || movie == null || !movieRepo.existsById(id)) {
            return false;
        }
        movie.setId(id);
        movieRepo.save(movie);
        return true;
    }

    public boolean deleteById(String id) {
        if (id == null || !movieRepo.existsById(id)) {
            return false;
        }
        movieRepo.deleteById(id);
        return true;
    }
}
