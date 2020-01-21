package com.example.project.service;

import com.example.project.entiy.Movie;
import com.example.project.entiy.PlannedMovie;
import com.example.project.entiy.User;
import com.example.project.entiy.WatchedMovie;
import com.example.project.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    public boolean add(User user) {
        if (user == null || isExistsById(user.getId()) ||
                isExistsByUsername(user.getUsername())) {
            return false;
        }
        userRepo.save(user);
        return true;
    }

    public User.CoreInfo findCoreById(String id) {
        return userRepo.findCoreById(id).orElse(null);
    }

    public boolean updateById(String id, User user) {
        if (id == null || user == null || !isExistsById(id)) {
            return false;
        }
        user.setId(id);
        userRepo.save(user);
        return true;
    }

    public boolean deleteById(String id) {
        if (id == null || !isExistsById(id)) {
            return false;
        }
        userRepo.deleteById(id);
        return true;
    }

    public boolean isExistsById(String id) {
        if (id == null || id.length() != User.MAX_ID_LENGTH) {
            return false;
        }
        return userRepo.existsById(id);
    }

    public boolean isExistsByUsername(String username) {
        if (username == null || username.length() > User.MAX_USERNAME_LENGTH) {
            return false;
        }
        return userRepo.existsByUsername(username);
    }

    public User findById(String id) {
        return userRepo.findById(id).orElse(null);
    }

    public User.CoreInfo findCoreByUsername(String username) {
        return userRepo.findCoreByUsername(username).orElse(null);
    }

    public User.Credentials findCredentialsByUsername(String username) {
        return userRepo.findCredentialsByUsername(username).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

    public List<PlannedMovie> findPlannedMovies(String id) {
        return findPlannedMovies(findById(id));
    }

    public List<PlannedMovie> findPlannedMovies(User user) {
        if (user == null) {
            return null;
        }
        return user.getPlannedMovies();
    }

    public List<WatchedMovie> findWatchedMovies(String id) {
        return findWatchedMovies(findById(id));
    }

    public List<WatchedMovie> findWatchedMovies(User user) {
        if (user == null) {
            return null;
        }
        return user.getWatchedMovies();
    }
}
