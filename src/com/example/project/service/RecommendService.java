package com.example.project.service;

import com.example.project.model.Genre;
import com.example.project.model.Movie;
import com.example.project.model.Type;
import com.example.project.model.User;
import com.example.project.repos.MovieRepos;
import com.example.project.repos.UserRepos;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

@Service
@AllArgsConstructor
public class RecommendService {

    private final UserService userService;
    private final MovieService movieService;
    private final UserRepos userRepos;
    private final MovieRepos movieRepos;

    private List<Movie> getDefaultRecommends(int pageSize, int page) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("popularity").descending().and(Sort.by("id")));
        return movieRepos.findAll(pageable).toList();
    }

    private List<Movie> getWatched(User user) {
        List<Movie> watchedMovies = new ArrayList<>();
        user.getListedMovies().forEach(listedMovie -> {
            if (listedMovie.getMark() > 0) {
                watchedMovies.add(listedMovie.getMovie());
            }
        });
        return watchedMovies;
    }

    private List<Genre> getFavoriteGenres(List<Movie> watchedMovies) {
        Map<Integer, Integer> genreStat = new HashMap<>();
        watchedMovies.forEach(movie -> {
            movie.getGenres().forEach(genre -> {
                Integer counter = genreStat.getOrDefault(genre.getId(), 0);
                genreStat.put(genre.getId(), counter + 1);
            });
        });
        List<Genre> genres = new ArrayList<>();
        genreStat.entrySet().stream()
                .sorted(Map.Entry.comparingByValue()).forEach(integerIntegerEntry -> {
                    genres.add(new Genre(integerIntegerEntry.getKey(), ""));
        });
        return genres;
    }

    private List<Type> getFavoriteTypes(List<Movie> watchedMovies) {
        // TODO: implement
        return new ArrayList<>();
    }

    public List<Movie> getRecommendsForUser(String userId) {
        Optional<User> optionalUser = userService.findUserById(userId);
        if (!optionalUser.isPresent()) {
            return new ArrayList<>();
        }
        // TODO: implement
        return getDefaultRecommends(10, 0);
    }
}
