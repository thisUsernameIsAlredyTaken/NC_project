package com.example.project.service;


import com.example.project.model.Movie;
import com.example.project.repos.MovieRepos;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepos movieRepos;
    private final UserService userService;

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

    public Optional<Movie> findMovieById(String id) {
        if (id.length() > getMaxIdLength()) {
            return Optional.empty();
        }
        return movieRepos.findById(id);
    }

    public Optional<Movie> updateMovie(Movie movie) {
        Optional<Movie> optionalOldMovie = findMovieById(movie.getId());
        optionalOldMovie.ifPresent(oldMovie -> movieRepos.save(movie));
        return optionalOldMovie;
    }

    public Optional<Movie> deleteMovieById(String id) {
        Optional<Movie> optionalDeletedMovie = findMovieById(id);
        optionalDeletedMovie.ifPresent(movieRepos::delete);
        return optionalDeletedMovie;
    }

    //
    private List<Movie> findAllMoviesSortedByPopularity(int pageSize, int page) {
        Pageable pageRequest = PageRequest.of(page, pageSize, Sort.by("popularity").descending().and(Sort.by("id")));
        return movieRepos.findAll(pageRequest).toList();
    }
}
