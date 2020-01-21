package com.example.project.service;

import com.example.project.entiy.Movie;
import com.example.project.entiy.User;
import com.example.project.repository.MovieRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepo movieRepo;

    public boolean add(Movie movie) {
        if (movie == null || isExistsById(movie.getId())) {
            return false;
        }
        movieRepo.save(movie);
        return false;
    }

    public Movie findById(String id) {
        return movieRepo.findById(id).orElse(null);
    }

    public boolean updateById(String id, Movie movie) {
        if (id == null || movie == null || !isExistsById(id)) {
            return false;
        }
        movie.setId(id);
        movieRepo.save(movie);
        return true;
    }

    public boolean isExistsById(String id) {
        if (id == null || id.length() != Movie.MAX_ID_LENGTH) {
            return false;
        }
        return movieRepo.existsById(id);
    }

    public boolean deleteById(String id) {
        if (id == null || !isExistsById(id)) {
            return false;
        }
        movieRepo.deleteById(id);
        return true;
    }

    public List<Movie> search(String pattern, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize,
                Sort.by("popularity").descending()
                        .and(Sort.by("id")));
        pattern = String.format("%%%s%%", pattern.toLowerCase());
        return movieRepo.search(pattern, pageable);
    }

    public List<Movie> getDefaultRecommend() {
        return movieRepo.getDefaultRecommend();
    }
}
