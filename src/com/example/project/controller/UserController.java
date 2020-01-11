package com.example.project.controller;

import com.example.project.entiy.PlannedMovie;
import com.example.project.entiy.User;
import com.example.project.entiy.WatchedMovie;
import com.example.project.service.UserActionService;
import com.example.project.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @GetMapping("p/{id}/stat")
    public User.Stat getStatistic(@PathVariable String id,
                                  HttpServletResponse response) {
        throw new NotImplementedException();
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

    @GetMapping("me")
    public User.CoreInfo readMe(HttpServletRequest request) {
        UsernamePasswordAuthenticationToken principal = (UsernamePasswordAuthenticationToken) request.getUserPrincipal();
        System.out.println(request.getUserPrincipal());
        System.out.println(principal);
        System.out.println();

        return userService.findCoreByUsername(request.getUserPrincipal().getName());
    }

    @PutMapping("me")
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

    @GetMapping("me/watched")
    public List<WatchedMovie> getMyWatched(HttpServletRequest request) {
        User user = userService.findByUsername(request.getUserPrincipal().getName());
        return userService.findWatchedMovies(user);
    }

    @GetMapping("me/planned")
    public List<PlannedMovie> getMyPlanned(HttpServletRequest request) {
        User user = userService.findByUsername(request.getUserPrincipal().getName());
        return userService.findPlannedMovies(user);
    }

    @PatchMapping("me/planned")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addMyPlanned(@RequestParam String movieId,
                             HttpServletRequest request) {
        String id = userService.findCoreByUsername(request.getUserPrincipal().getName()).getId();
        userActionService.addPlanned(id, movieId);
    }

    @PatchMapping("me/watched")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addMyWatched(@RequestParam String movieId,
                             @RequestParam(required = false) Integer rating,
                             HttpServletRequest request) {
        String id = userService.findCoreByUsername(request.getUserPrincipal().getName()).getId();
        userActionService.addWatched(id, movieId, rating);
    }

    @DeleteMapping("me/listed/{movieId}")
    public void deleteMyListed(@PathVariable String movieId,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        String id = userService.findCoreByUsername(request.getUserPrincipal().getName()).getId();
        if (userActionService.deleteListed(id, movieId)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private User requestUser(String id, HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken userPrincipal =
                (UsernamePasswordAuthenticationToken) request.getUserPrincipal();
        if (userPrincipal == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        } else {
            User user = userService.findByUsername(userPrincipal.getName());
            if (!userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) &&
                    !id.equals(user.getId())) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return null;
            }
            return user;
        }
    }
}
