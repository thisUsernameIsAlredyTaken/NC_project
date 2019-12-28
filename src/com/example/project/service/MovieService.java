package com.example.project.service;


import com.example.project.model.ListedMovie;
import com.example.project.model.Movie;
import com.example.project.model.User;
import com.example.project.repos.MovieRepos;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepos movieRepos;

    private int getMaxIdLength() {
        return movieRepos.getMaxIdLength();
    }


    // CRUD
    public Optional<Movie> addMovie(Movie movie) {
        Optional<Movie> optionalExistingMovie = movieRepos.findById(movie.getId());
        if (!optionalExistingMovie.isPresent()) {
            movieRepos.save(movie);
        }
        return optionalExistingMovie;
    }

    public Optional<Movie> getMovieById(String id) {
        if (id.length() > getMaxIdLength()) {
            return Optional.empty();
        }
        return movieRepos.findById(id);
    }

    public Optional<Movie> updateMovie(Movie movie) {
        Optional<Movie> optionalOldMovie = getMovieById(movie.getId());
        optionalOldMovie.ifPresent(oldMovie -> movieRepos.save(movie));
        return optionalOldMovie;
    }

    public Optional<Movie> deleteMovieById(String id) {
        Optional<Movie> optionalMovie = getMovieById(id);
        optionalMovie.ifPresent(movieRepos::delete);
        return optionalMovie;
    }
}
