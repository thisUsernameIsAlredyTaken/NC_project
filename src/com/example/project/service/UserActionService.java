package com.example.project.service;

import com.example.project.entiy.Movie;
import com.example.project.entiy.PlannedMovie;
import com.example.project.entiy.User;
import com.example.project.entiy.WatchedMovie;
import com.example.project.repository.PlannedMovieRepo;
import com.example.project.repository.WatchedMovieRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class UserActionService {

    private final UserService userService;
    private final MovieService movieService;
    private final WatchedMovieRepo watchedMovieRepo;
    private final PlannedMovieRepo plannedMovieRepo;

    public String movieStatus(String userId, String movieId) {
        return movieStatus(userService.findById(userId), movieId);
    }

    public String movieStatus(User user, String movieId) {
        if (user == null || movieId == null) {
            return "none";
        }
        for (WatchedMovie watchedMovie : user.getWatchedMovies()) {
            if (movieId.equals(watchedMovie.getMovie().getId())) {
                return "watched";
            }
        }
        for (PlannedMovie plannedMovie : user.getPlannedMovies()) {
            if (movieId.equals(plannedMovie.getMovie().getId())) {
                return "planned";
            }
        }
        return "none";
    }

    public boolean addPlanned(String userId, String movieId) {
        Movie movie = movieService.findById(movieId);
        User user = userService.findById(userId);
        if (movie == null || user == null) {
            return false;
        }
        deleteListed(user, movieId);
        PlannedMovie plannedMovie = new PlannedMovie();
        plannedMovie.setMovie(movie);
        plannedMovie.setUser(user);
        plannedMovie.setDate(new Date());

        plannedMovieRepo.save(plannedMovie);
        return true;
    }

    public boolean addWatched(String userId, String movieId, Integer rating) {
        if (rating < 1 || rating > 10) {
            return false;
        }
        User user = userService.findById(userId);
        Movie movie = movieService.findById(movieId);
        if (user == null || movie == null) {
            return false;
        }
        deleteListed(user, movieId);
        WatchedMovie watchedMovie = new WatchedMovie();
        watchedMovie.setUser(user);
        watchedMovie.setMovie(movie);
        watchedMovie.setRating(rating);
        watchedMovie.setDate(new Date());

        watchedMovieRepo.save(watchedMovie);
        return true;
    }

    public boolean deleteListed(String userId, String movieId) {
        return deleteListed(userService.findById(userId), movieId);
    }

    public boolean deleteListed(User user, String movieId) {
        if (user == null) {
            return false;
        }
        return deleteWatched(user.getWatchedMovies(), movieId) ||
                deletePlanned(user.getPlannedMovies(), movieId);
    }

    public boolean deleteWatched(String userId, String movieId) {
        return deleteWatched(userService.findWatchedMovies(userId), movieId);
    }

    public boolean deleteWatched(List<WatchedMovie> watchedMovies, String movieId) {
        if (watchedMovies == null || movieId == null) {
            return false;
        }
        for (WatchedMovie watchedMovie : watchedMovies) {
            if (movieId.equals(watchedMovie.getMovie().getId())) {
                watchedMovieRepo.deleteById(watchedMovie.getId());
                return true;
            }
        }
        return false;
    }

    public boolean deletePlanned(String userId, String movieId) {
        return deletePlanned(userService.findPlannedMovies(userId), movieId);
    }

    public boolean deletePlanned(List<PlannedMovie> plannedMovies, String movieId) {
        if (plannedMovies == null || movieId == null) {
            return false;
        }
        for (PlannedMovie plannedMovie : plannedMovies) {
            if (movieId.equals(plannedMovie.getMovie().getId())) {
                plannedMovieRepo.deleteById(plannedMovie.getId());
                return true;
            }
        }
        return false;
    }
}
