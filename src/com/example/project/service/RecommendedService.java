package com.example.project.service;

import com.example.project.model.*;
import com.example.project.repos.GenreRepos;
import com.example.project.repos.MovieRepos;
import com.example.project.repos.TypeRepos;
import com.example.project.service.crud.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecommendedService {

    private final UserService userService;
    private final MovieRepos movieRepos;
    private final GenreRepos genreRepos;
    private final TypeRepos typeRepos;

    private List<Movie> getDefaultRecommended(int pageSize) {
        Pageable pageable = PageRequest.of(0, pageSize, Sort.by("popularity").descending().and(Sort.by("id")));
        return movieRepos.findAll(pageable).toList();
    }

    public List<ListedMovie> getUsersWatchedMovies(String userId) {
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
                throw new NoSuchElementException("Genre with id " + genreId + " doesn't found");
            }
            favoriteGenresSorted.add(optionalGenre.get());
        }
        return favoriteGenresSorted;
    }

    public List<Type> getFavoriteTypes(List<ListedMovie> watchedMovies) {
        Map<Integer, Integer> favoriteTypes = new HashMap<>();
        watchedMovies.forEach(listedMovie -> {
            Type type = listedMovie.getMovie().getType();
            int typeId = type.getId();
            int counter = favoriteTypes.getOrDefault(typeId, 0);
            counter += listedMovie.getMark();
            favoriteTypes.put(typeId, counter);
        });
        List<Integer> favoriteTypeIdSorted = new ArrayList<>();
        favoriteTypes.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .collect(Collectors.toList())
                .forEach(integerIntegerEntry ->
                        favoriteTypeIdSorted.add(integerIntegerEntry.getKey()));
        List<Type> favoriteTypesSorted = new ArrayList<>(favoriteTypeIdSorted.size());
        for (Integer typeId : favoriteTypeIdSorted) {
            Optional<Type> optionalType = typeRepos.findById(typeId);
            if (!optionalType.isPresent()) {
                throw new NoSuchElementException("Type with id " + typeId + " doesn't exists");
            }
            favoriteTypesSorted.add(optionalType.get());
        }
        return favoriteTypesSorted;
    }

    public List<Movie> getRecommendedByUserId(String userId) {
        List<ListedMovie> watchedMovies = getUsersWatchedMovies(userId);
        if (watchedMovies.isEmpty()) {
            return getDefaultRecommended(10);
        }
        List<Type> favoriteTypes = getFavoriteTypes(watchedMovies);
        List<Genre> favoriteGenres = getFavoriteGenres(watchedMovies);
        String typeIds = "";
        String genreIds = "";
        for (Genre genre : favoriteGenres) {
            genreIds += genre.getId() + ",";
        }
        for (Type type : favoriteTypes) {
            typeIds += type.getId() + ",";
        }
        return movieRepos.getRecommended(typeIds, genreIds, 10);
    }
}
