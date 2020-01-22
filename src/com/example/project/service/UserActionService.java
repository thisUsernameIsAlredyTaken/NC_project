package com.example.project.service;

import com.example.project.entiy.Movie;
import com.example.project.entiy.PlannedMovie;
import com.example.project.entiy.User;
import com.example.project.entiy.WatchedMovie;
import com.example.project.repository.MovieRepo;
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
    private final MovieRepo movieRepo;
    private final WatchedMovieRepo watchedMovieRepo;
    private final PlannedMovieRepo plannedMovieRepo;

    public boolean addPlanned(User user, String movieId) {
        Movie movie = movieService.findById(movieId);
        if (movie == null || user == null) {
            return false;
        }
        deleteListedById(user.getId(), movieId);
        PlannedMovie plannedMovie = new PlannedMovie();
        plannedMovie.setMovie(movie);
        plannedMovie.setUser(user);
        plannedMovie.setDate(new Date());

        plannedMovieRepo.save(plannedMovie);
        return true;
    }

    public boolean addPlannedById(String userId, String movieId) {
        return addPlanned(userService.findById(userId), movieId);
    }

    public boolean addPlannedByUsername(String username, String movieId) {
        return addPlanned(userService.findByUsername(username), movieId);
    }

    public boolean addWatched(User user, String movieId, Integer rating) {
        if (rating < 1 || rating > 10) {
            return false;
        }
        Movie movie = movieService.findById(movieId);
        if (user == null || movie == null) {
            return false;
        }
        deleteListedById(user.getId(), movieId);
        WatchedMovie watchedMovie = new WatchedMovie();
        watchedMovie.setUser(user);
        watchedMovie.setMovie(movie);
        watchedMovie.setRating(rating);
        watchedMovie.setDate(new Date());

        watchedMovieRepo.save(watchedMovie);
        return true;
    }

    public boolean addWatchedById(String userId, String movieId, Integer rating) {
        return addWatched(userService.findById(userId), movieId, rating);
    }

    public boolean addWatchedByUsername(String username, String movieId, Integer rating) {
        return addWatched(userService.findByUsername(username), movieId, rating);
    }

    public boolean deleteListedById(String userId, String movieId) {
        return deleteWatchedById(userId, movieId) ||
                deletePlannedById(userId, movieId);
    }

    public boolean deleteListedByUsername(String username, String movieId) {
        return deleteWatchedByUsername(username, movieId) ||
                deletePlannedByUsername(username, movieId);
    }

    public boolean deletePlanned(List<PlannedMovie.NoUser> plannedMovies, String movieId) {
        for (PlannedMovie.NoUser plannedMovie : plannedMovies) {
            if (movieId.equals(plannedMovie.getMovie().getId())) {
                plannedMovieRepo.deleteById(plannedMovie.getId());
                return true;
            }
        }
        return false;
    }

    public boolean deletePlannedById(String userId, String movieId) {
        return deletePlanned(plannedMovieRepo.findByUserId(userId), movieId);
    }

    public boolean deletePlannedByUsername(String username, String movieId) {
        return deletePlanned(plannedMovieRepo.findByUsername(username), movieId);
    }

    public boolean deleteWatched(List<WatchedMovie.NoUser> watchedMovies, String movieId) {
        for (WatchedMovie.NoUser watchedMovie : watchedMovies) {
            if (movieId.equals(watchedMovie.getMovie().getId())) {
                watchedMovieRepo.deleteById(watchedMovie.getId());
                return true;
            }
        }
        return false;
    }

    public boolean deleteWatchedById(String userId, String movieId) {
        return deleteWatched(watchedMovieRepo.findByUserId(userId), movieId);
    }

    public boolean deleteWatchedByUsername(String username, String movieId) {
        return deleteWatched(watchedMovieRepo.findByUsername(username), movieId);
    }

    public List<Movie> getRecommendById(String userId) {
        if (!userService.isExistsById(userId)) {
            return null;
        } else if (watchedMovieRepo.countById(userId) < 8) {
            return movieService.getDefaultRecommend();
        }
        return movieRepo.getRecommendByUserId(userId);
    }

    public List<Movie> getRecommendByUsername(String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return null;
        }
        return getRecommendById(user.getId());
    }
}
