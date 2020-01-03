package com.example.project.service;

import com.example.project.model.*;
import com.example.project.repos.GenreRepos;
import com.example.project.repos.MovieRepos;
import com.example.project.repos.UserRepos;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecommendService {

    private final UserService userService;
    private final MovieService movieService;
    private final UserRepos userRepos;
    private final MovieRepos movieRepos;
    private final GenreRepos genreRepos;

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

    public List<ListedMovie> getUsersWatched(String userId) {
        User user = userService.findUserById(userId).orElse(null);
        if (user == null) {
            return new ArrayList<>();
        }
        List<ListedMovie> watchedMovies = new ArrayList<>();
        user.getListedMovies().forEach(listedMovie -> {
            if (listedMovie.getMark() > 0) {
                watchedMovies.add(listedMovie);
            }
        });
        watchedMovies.sort((lm1, lm2) -> {
            int result = lm2.getMark() - lm1.getMark();
            if (result == 0) {
                return lm2.getMovie().getPopularity() - lm1.getMovie().getPopularity();
            }
            return result;
        });
        return watchedMovies;
    }

    public List<Genre> getFavoriteGenres(List<ListedMovie> watchedMovies) {
        // TODO
        Map<Integer, Integer> favoriteGenres = new HashMap<>();
        watchedMovies.forEach(listedMovie -> listedMovie.getMovie().getGenres().forEach(genre -> {
            int genreId = genre.getId();
            int counter = favoriteGenres.getOrDefault(genreId, 0);
            counter += listedMovie.getMark();
            favoriteGenres.put(genreId, counter);
        }));
        List<Integer> favoriteGenresIdSorted = new ArrayList<>();
        favoriteGenres.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .collect(Collectors.toList())
                .forEach(integerIntegerEntry ->
                        favoriteGenresIdSorted.add(integerIntegerEntry.getKey()));

        List<Genre> favoriteGenresSorted = new ArrayList<>(favoriteGenres.size());
        for (Integer genreId : favoriteGenresIdSorted) {
            Optional<Genre> optionalGenre = genreRepos.findById(genreId);
            if (!optionalGenre.isPresent()) {
                throw new NoSuchElementException("Genre with id " + genreId + "doesn't found");
            }
            favoriteGenresSorted.add(optionalGenre.get());
        }
        return favoriteGenresSorted;
    }

    private List<Type> getFavoriteTypes(List<Movie> watchedMovies) {
        // TODO: implement
        return new ArrayList<>();
    }

    public List<Movie> getRecommendsForUser(String userId) {
        List<ListedMovie> watchedMovies = getUsersWatched(userId);
        if (watchedMovies.isEmpty()) {
            return getDefaultRecommends(10, 0);
        }
        List<Genre> favoriteGenres = getFavoriteGenres(watchedMovies);
        // TODO: implement
        return getDefaultRecommends(10, 0);
    }
}
