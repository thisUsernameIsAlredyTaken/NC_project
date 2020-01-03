package com.example.project.controller;


import com.example.project.model.Genre;
import com.example.project.model.ListedMovie;
import com.example.project.model.Movie;
import com.example.project.service.RecommendService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@AllArgsConstructor
public class RecommendsController {

    private final RecommendService recommendService;

    @GetMapping("user/{id}/recommends")
    public List<Movie> getRecommends(@PathVariable String id,
                                     HttpServletResponse response) {
        List<Movie> recommends = recommendService.getRecommendsForUser(id);
        if (recommends.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        return recommends;
    }

    @GetMapping("user/{id}/favoritegenres")
    public List<Genre> getFavoriteGenres(@PathVariable String id,
                                         HttpServletResponse response) {
        List<ListedMovie> listedMovies = recommendService.getUsersWatched(id);
        return recommendService.getFavoriteGenres(listedMovies);
    }
}