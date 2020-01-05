package com.example.project.controller;

import com.example.project.model.Movie;
import com.example.project.service.crud.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    // CRUD
    @PostMapping
    public void insertMovie(@RequestBody Movie movie,
                            HttpServletResponse response) {
        System.out.println("movie = " + movie);
        Optional<Movie> optionalExistingMovie = movieService.addMovie(movie);
        if (optionalExistingMovie.isPresent()) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            response.setStatus(HttpServletResponse.SC_CREATED);
        }
    }

    @GetMapping("{id}")
    public Optional<Movie> findMovieById(@PathVariable String id,
                                         HttpServletResponse response) {
        Optional<Movie> optionalMovie = movieService.findMovieById(id);

        if (optionalMovie.isPresent()) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return optionalMovie;
    }

    @PutMapping
    public void updateMovie(@RequestBody Movie movie,
                            HttpServletResponse response) {
        Optional<Movie> movieOldOptional = movieService.updateMovie(movie);
        if (movieOldOptional.isPresent()) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public void deleteMovieById(@PathVariable String id,
                                HttpServletResponse response) {
        Optional<Movie> optionalDeletedMovie = movieService.deleteMovieById(id);
        if (optionalDeletedMovie.isPresent()) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    //

}
