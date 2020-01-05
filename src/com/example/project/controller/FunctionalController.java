package com.example.project.controller;


import com.example.project.model.Genre;
import com.example.project.model.ListedMovie;
import com.example.project.model.Movie;
import com.example.project.model.Type;
import com.example.project.service.RecommendedService;
import com.example.project.service.SearchMovieService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
//    private final MovieRepos movieRepos;
//    private final TypeRepos typeRepos;

    @GetMapping("user/{id}/recommended")
    public List<Movie> getRecommended(@PathVariable String id,
                                      HttpServletResponse response) {
        List<Movie> recommends = recommendedService.getRecommendedByUserId(id);
        if (recommends.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return recommends;
    }

    @GetMapping("movie/search")
    public List<Movie> findMoviesByTitle(@RequestParam String pattern,
                                         @RequestParam(defaultValue = "0") int page) {
        return searchService.findMoviesByTitle(pattern, page);
    }

    // TEST
//    @GetMapping("movies/by-types")
//    public List<Movie> findByTypes(@RequestParam String typeIds,
//                                   @RequestParam int count) {
//        Set<Type> types = new HashSet<>();
//        for (String strId : typeIds.split(",")) {
//            int id = Integer.parseInt(strId);
//            types.add(typeRepos.findById(id).get());
//        }
//        Pageable pageable = PageRequest.of(0, count, Sort.by("id"));
//        return movieRepos.findByTypeIn(types, pageable);
//    }
//
//    @GetMapping("movies/by-types2")
//    public List<Movie> findByTypes2(@RequestParam int[] typeIds,
//                                    @RequestParam int count) {
//        Set<Type> types = new HashSet<>();
//        for (int id : typeIds) {
////            int id = Integer.parseInt(strId);
//            types.add(typeRepos.findById(id).get());
//        }
//        Pageable pageable = PageRequest.of(0, count, Sort.by("id"));
//        return movieRepos.findByTypeIn2(types, pageable);
//    }
//
//    @GetMapping("test/void-func")
//    public int voidFunc(@RequestParam int[] numbers) {
//        Set<Integer> ints = new HashSet<>();
//        for (int number : numbers) {
//            ints.add(number);
//        }
//        return movieRepos.callTest(ints);
//    }

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
