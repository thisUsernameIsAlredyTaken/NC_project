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
import java.util.List;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserAdminController {

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

    @GetMapping("find")
    public User.CoreInfo findByUsername(@RequestParam String username,
                                        HttpServletResponse response) {
        User.CoreInfo user = userService.findCoreByUsername(username);
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return user;
    }

    @GetMapping("p/{id}/watched")
    public List<WatchedMovie.NoUser> getWatched(@PathVariable String id,
                                                HttpServletResponse response) {
        List<WatchedMovie.NoUser> watchedMovies = userService.findWatchedById(id);
        if (watchedMovies == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return watchedMovies;
    }

    @GetMapping("p/{id}/planned")
    public List<PlannedMovie.NoUser> getPlanned(@PathVariable String id,
                                                HttpServletResponse response) {
        List<PlannedMovie.NoUser> plannedMovies = userService.findPlannedById(id);
        if (plannedMovies == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return plannedMovies;
    }

    @PatchMapping("p/{userId}/planned")
    public void addPlanned(@PathVariable String userId,
                           @RequestParam String movieId,
                           HttpServletResponse response) {
        if (userActionService.addPlannedById(userId, movieId)) {
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
        if (userActionService.addWatchedById(userId, movieId, rating)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @DeleteMapping("p/{userId}/listed/{movieId}")
    public void deleteListed(@PathVariable String userId,
                             @PathVariable String movieId,
                             HttpServletResponse response) {
        if (userActionService.deleteListedById(userId, movieId)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @DeleteMapping("p/{userId}/watched/{movieId}")
    public void deleteWatched(@PathVariable String userId,
                              @PathVariable String movieId,
                              HttpServletResponse response) {
        if (userActionService.deleteWatchedById(userId, movieId)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @DeleteMapping("p/{userId}/planned/{movieId}")
    public void deletePlanned(@PathVariable String userId,
                              @PathVariable String movieId,
                              HttpServletResponse response) {
        if (userActionService.deletePlannedById(userId, movieId)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @GetMapping("p/{userId}/recommend")
    public List<Movie> getRecommend(@PathVariable String userId,
                                    HttpServletResponse response) {
        List<Movie> recommend = userActionService.getRecommendById(userId);
        if (recommend == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return recommend;
    }
}
