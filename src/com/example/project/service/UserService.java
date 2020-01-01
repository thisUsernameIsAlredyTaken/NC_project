package com.example.project.service;

import com.example.project.model.User;
import com.example.project.repos.UserRepos;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepos userRepos;

    private int getMaxIdLength() {
        return userRepos.getMaxIdLength();
    }

    // CRUD
    public Optional<User> addUser(User user) {
        Optional<User> optionalExistingUser = userRepos.findById(user.getId());
        if (!optionalExistingUser.isPresent()) {
            userRepos.save(user);
        }
        return optionalExistingUser;
    }

    public Optional<User> findUserById(String id) {
        if (id.length() > getMaxIdLength()) {
            return Optional.empty();
        }
        return userRepos.findById(id);
    }

    public Optional<User> updateUser(User movie) {
        Optional<User> optionalOldUser = findUserById(movie.getId());
        optionalOldUser.ifPresent(oldMovie -> userRepos.save(movie));
        return optionalOldUser;
    }

    public Optional<User> deleteMovieById(String id) {
        Optional<User> optionalDeletedUser = findUserById(id);
        optionalDeletedUser.ifPresent(userRepos::delete);
        return optionalDeletedUser;
    }
}
