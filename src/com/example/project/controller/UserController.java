package com.example.project.controller;

import com.example.project.model.User;
import com.example.project.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    // CRUD
    @PostMapping
    public void insertUser(@RequestBody User user,
                           HttpServletResponse response) {
        Optional<User> optionalExistingUser = userService.addUser(user);
        if (optionalExistingUser.isPresent()) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            response.setStatus(HttpServletResponse.SC_CREATED);
        }
    }

    @GetMapping("{id}")
    public User findUserById(@PathVariable String id,
                                       HttpServletResponse response) {
        Optional<User> optionalUser = userService.findUserById(id);
        if (optionalUser.isPresent()) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return optionalUser.orElse(null);
    }

    @PutMapping
    public void updateUser(@RequestBody User user,
                           HttpServletResponse response) {
        Optional<User> optionOldUser = userService.updateUser(user);
        if (optionOldUser.isPresent()) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public void deleteUserById(@PathVariable String id,
                               HttpServletResponse response) {
        Optional<User> optionalDeletedUser = userService.deleteMovieById(id);
        if (optionalDeletedUser.isPresent()) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    //

}
