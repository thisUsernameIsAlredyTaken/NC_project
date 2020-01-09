package com.example.project.controller;

import com.example.project.entiy.Movie;
import com.example.project.entiy.PlannedMovie;
import com.example.project.entiy.User;
import com.example.project.entiy.WatchedMovie;
import com.example.project.service.UserActionService;
import com.example.project.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserActionService userActionService;

    @PostMapping
    public void create(@RequestBody User user,
                       HttpServletResponse response) {
        if (userService.add(user)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }

    @GetMapping("p/{id}")
    public User.CoreInfo read(@PathVariable String id,
                              HttpServletResponse response) {
        User.CoreInfo user = userService.findCoreById(id);
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return user;
    }

    @PutMapping("p/{id}")
    public void update(@PathVariable String id,
                       @RequestBody User user,
                       HttpServletResponse response) {
        if (userService.updateById(id, user)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @DeleteMapping("p/{id}")
    public void delete(@PathVariable String id,
                       HttpServletResponse response) {
        if (userService.deleteById(id)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @GetMapping("p/{id}/watched")
    public List<WatchedMovie> getWatched(@PathVariable String id,
                                         HttpServletResponse response) {
        List<WatchedMovie> watchedMovies = userService.findWatchedMovies(id);
        if (watchedMovies == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return watchedMovies;
    }

    @GetMapping("p/{id}/planned")
    public List<PlannedMovie> getPlanned(@PathVariable String id,
                                         HttpServletResponse response) {
        List<PlannedMovie> plannedMovies = userService.findPlannedMovies(id);
        if (plannedMovies == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return plannedMovies;
    }

    @PatchMapping("p/{userId}/planned")
    public void addPlanned(@PathVariable String userId,
                           @RequestParam String movieId,
                           HttpServletResponse response) {
        if (userActionService.addPlanned(userId, movieId)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @PatchMapping("p/{userId}/watched")
    public void addWatched(@PathVariable String userId,
                           @RequestParam String movieId,
                           @RequestParam(required = false) Integer rating,
                           HttpServletResponse response) {
        if (userActionService.addWatched(userId, movieId, rating)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @DeleteMapping("p/{userId}/listed/{movieId}")
    public void deleteListed(@PathVariable String userId,
                             @PathVariable String movieId,
                             HttpServletResponse response) {
        if (userActionService.deleteListed(userId, movieId)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
