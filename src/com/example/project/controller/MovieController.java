package com.example.project.controller;

import com.example.project.entiy.Movie;
import com.example.project.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("movie")
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public void create(@RequestBody Movie movie,
                       HttpServletResponse response) {
        if (movieService.add(movie)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }

    @GetMapping("p/{id}")
    public Movie read(@PathVariable String id,
                      HttpServletResponse response) {
        Movie movie = movieService.findById(id);
        if (movie == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return movie;
    }

    @PutMapping("p/{id}")
    public void update(@PathVariable String id,
                       @RequestBody Movie movie,
                       HttpServletResponse response) {
        if (movieService.updateById(id, movie)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @DeleteMapping("p/{id}")
    public void delete(@PathVariable String id,
                       HttpServletResponse response) {
        if (movieService.deleteById(id)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @GetMapping("search")
    public List<Movie> search(@RequestParam String pattern,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int pageSize) {
        return movieService.search(pattern, page, pageSize);
    }
}
