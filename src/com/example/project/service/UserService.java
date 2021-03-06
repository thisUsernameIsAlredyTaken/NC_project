package com.example.project.service;

import com.example.project.entiy.PlannedMovie;
import com.example.project.entiy.User;
import com.example.project.entiy.WatchedMovie;
import com.example.project.repository.PlannedMovieRepo;
import com.example.project.repository.UserRepo;
import com.example.project.repository.WatchedMovieRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final WatchedMovieRepo watchedMovieRepo;
    private final PlannedMovieRepo plannedMovieRepo;

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

    public List<PlannedMovie.NoUser> findPlannedById(String userId) {
        return plannedMovieRepo.findByUserId(userId);
    }

    public List<PlannedMovie.NoUser> findPlannedByUsername(String username) {
        return plannedMovieRepo.findByUsername(username);
    }

    public List<WatchedMovie.NoUser> findWatchedById(String userId) {
        return watchedMovieRepo.findByUserId(userId);
    }

    public List<WatchedMovie.NoUser> findWatchedByUsername(String username) {
        return watchedMovieRepo.findByUsername(username);
    }
}
