package com.example.project.controller;

import com.example.project.model.Genre;
import com.example.project.model.ListedMovie;
import com.example.project.model.Movie;
import com.example.project.model.User;
import com.example.project.service.RecommendedService;
import com.example.project.service.SearchService;
import com.example.project.service.crud.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@AllArgsConstructor
public class FunctionalController {

    private final RecommendedService recommendedService;
    private final UserService userService;
    private final SearchService searchService;

    @GetMapping("user/{userId}/recommended")
    public List<Movie> getRecommended(@PathVariable String userId) {
        return recommendedService.getRecommendedByUserId(userId);
    }

    @GetMapping("user/{userId}/favorite-genres")
    public List<Genre> getFavoriteGenres(@PathVariable String userId,
                                         HttpServletResponse response) {
        List<ListedMovie> usersWatchedMovies = recommendedService.getUsersWatchedMovies(userId);
        if (usersWatchedMovies.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ArrayList<>();
        }
        List<Genre> favoriteGenres = recommendedService.getFavoriteGenres(usersWatchedMovies);
        if (favoriteGenres.size() > 3) {
            favoriteGenres = favoriteGenres.subList(0, 3);
        }
        return favoriteGenres;
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

    @GetMapping("user/{userId}/watched")
    public List<ListedMovie> getUsersWatched(@PathVariable String userId,
                                             HttpServletResponse response) {
        Optional<User> optionalUser = userService.findUserById(userId);
        List<ListedMovie> listedMovies;
        if (optionalUser.isPresent()) {
            listedMovies = new ArrayList<>();
            for (ListedMovie listedMovie : optionalUser.get().getListedMovies()) {
                if (listedMovie.getMark() > 0) {
                    listedMovies.add(listedMovie);
                }
            }
            listedMovies.sort((lm1, lm2) -> lm2.getDate().compareTo(lm1.getDate()));
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            listedMovies = new ArrayList<>();
        }
        return listedMovies;
    }

    @GetMapping("user/{userId}/planned")
    public List<Movie> getUsersPlanned(@PathVariable String userId,
                                       HttpServletResponse response) {
        Optional<User> optionalUser = userService.findUserById(userId);
        List<Movie> plannedMovies;
        if (optionalUser.isPresent()) {
            plannedMovies = new ArrayList<>();
            for (ListedMovie listedMovie : optionalUser.get().getListedMovies()) {
                if (listedMovie.getMark() == -1) {
                    plannedMovies.add(listedMovie.getMovie());
                }
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            plannedMovies = new ArrayList<>();
        }
        return plannedMovies;
    }

//    @GetMapping("user/{userId}/favorite-types")
//    public List<Type> getFavoriteTypes(@PathVariable String userId,
//                                       HttpServletResponse response) {
//        List<ListedMovie> listedMovies = recommendedService.getUsersWatchedMovies(userId);
//        return recommendedService.getFavoriteTypes(listedMovies);
//    }
}
