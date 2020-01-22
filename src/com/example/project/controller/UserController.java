package com.example.project.controller;

import com.example.project.entiy.Movie;
import com.example.project.entiy.PlannedMovie;
import com.example.project.entiy.User;
import com.example.project.entiy.WatchedMovie;
import com.example.project.service.UserActionService;
import com.example.project.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("user/me")
public class UserController {

    private final UserService userService;
    private final UserActionService userActionService;

    @GetMapping
    public User.CoreInfo readMe(HttpServletRequest request) {
        return userService.findCoreByUsername(request.getUserPrincipal().getName());
    }

    @PutMapping
    public void updateMe(@RequestBody User user,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        String id = userService.findCoreByUsername(request.getUserPrincipal().getName()).getUsername();
        if (userService.updateById(id, user)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }

    @GetMapping("watched")
    public List<WatchedMovie.NoUser> getMyWatched(HttpServletRequest request) {
        return userService.findWatchedByUsername(request.getUserPrincipal().getName());
    }

    @GetMapping("planned")
    public List<PlannedMovie.NoUser> getMyPlanned(HttpServletRequest request) {
        return userService.findPlannedByUsername(request.getUserPrincipal().getName());
    }

    @PatchMapping("planned")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addMyPlanned(@RequestParam String movieId,
                             HttpServletRequest request) {
        userActionService.addPlannedByUsername(request.getUserPrincipal().getName(), movieId);
    }

    @PatchMapping("watched")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addMyWatched(@RequestParam String movieId,
                             @RequestParam(required = false) Integer rating,
                             HttpServletRequest request) {
        userActionService.addWatchedByUsername(request.getUserPrincipal().getName(), movieId, rating);
    }

    @DeleteMapping("listed/{movieId}")
    public void deleteMyListed(@PathVariable String movieId,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        if (userActionService.deleteListedByUsername(request.getUserPrincipal().getName(), movieId)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @DeleteMapping("watched/{movieId}")
    public void deleteMyWatched(@PathVariable String movieId,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        if (userActionService.deleteWatchedByUsername(request.getUserPrincipal().getName(), movieId)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @DeleteMapping("planned/{movieId}")
    public void deleteMyPlanned(@PathVariable String movieId,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        if (userActionService.deletePlannedByUsername(request.getUserPrincipal().getName(), movieId)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @GetMapping("recommend")
    public List<Movie> getRecommendForMe(HttpServletRequest request) {
        return userActionService.getRecommendByUsername(request.getUserPrincipal().getName());
    }
}
