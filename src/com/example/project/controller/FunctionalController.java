package com.example.project.controller;

import com.example.project.model.Genre;
import com.example.project.model.ListedMovie;
import com.example.project.model.Movie;
import com.example.project.model.Type;
import com.example.project.service.RecommendedService;
import com.example.project.service.SearchMovieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@AllArgsConstructor
public class FunctionalController {

    private final RecommendedService recommendedService;
    private final SearchMovieService searchService;

    @GetMapping("user/{id}/recommended")
    public List<Movie> getRecommended(@PathVariable String id) {
        return recommendedService.getRecommendedByUserId(id);
    }

    @GetMapping("movie/search")
    public List<Movie> findMoviesByTitle(@RequestParam String pattern,
                                         @RequestParam(defaultValue = "0") int page,
                                         HttpServletResponse response) {
        List<Movie> founded = searchService.findMoviesByTitle(pattern, page);
        if (founded.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return founded;
    }

    @GetMapping("user/{userId}/favorite-genres")
    public List<Genre> getFavoriteGenres(@PathVariable String userId,
                                         HttpServletResponse response) {
        List<ListedMovie> listedMovies = recommendedService.getUsersWatchedMovies(userId);
        return recommendedService.getFavoriteGenres(listedMovies);
    }

    @GetMapping("user/{userId}/favorite-types")
    public List<Type> getFavoriteTypes(@PathVariable String userId,
                                       HttpServletResponse response) {
        List<ListedMovie> listedMovies = recommendedService.getUsersWatchedMovies(userId);
        return recommendedService.getFavoriteTypes(listedMovies);
    }
}
