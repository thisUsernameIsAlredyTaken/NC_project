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
        Optional<User> optionalUser = userService.findUserById(userId);
        if (!optionalUser.isPresent()) {
            return new ArrayList<>();
        }
        User user = optionalUser.get();
        List<ListedMovie> watchedMovies = new ArrayList<>();
        user.getListedMovies().forEach(listedMovie -> {
            if (listedMovie.getMark() > 0) {
                watchedMovies.add(listedMovie);
            }
        });
        watchedMovies.sort((lm1, lm2) -> {
            int result = lm2.getMark() - lm1.getMark();
            if (result == 0) {
                result = lm2.getMovie().getPopularity() - lm1.getMovie().getPopularity();
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
        List<String> watchedMovieIds = new ArrayList<>();
        for (ListedMovie watchedMovie : watchedMovies) {
            watchedMovieIds.add(watchedMovie.getMovie().getId());
        }
        List<Type> favoriteTypes = getFavoriteTypes(watchedMovies);
//        List<Type> favoriteTypes = new ArrayList<>();
//        favoriteTypes.add(typeRepos.findById())
        List<Genre> favoriteGenres = getFavoriteGenres(watchedMovies);
        if (favoriteTypes.size() > 3) {
            favoriteTypes = favoriteTypes.subList(0, 3);
        }
        if (favoriteTypes.size() < 2) {
            favoriteTypes.clear();
            favoriteTypes.addAll(typeRepos.findAll());
        }
        if (favoriteGenres.size() > 3) {
            favoriteGenres = favoriteGenres.subList(0, 3);
        }
        if (favoriteGenres.size() < 2) {
            return getDefaultRecommended(0);
        }
        String typeIds = "";
        String genreIds = "";
        for (Genre genre : favoriteGenres) {
            genreIds += genre.getId() + ",";
        }
        if (genreIds.length() > 0)
            genreIds = genreIds.substring(0, genreIds.length() - 1);
        for (Type type : favoriteTypes) {
            typeIds += type.getId() + ",";
        }
        if (typeIds.length() > 0)
            typeIds = typeIds.substring(0, typeIds.length() - 1);
        Pageable pageable = PageRequest.of(0, 10);
        return movieRepos.getRecommended(typeIds, genreIds, watchedMovieIds, pageable);
    }
}
