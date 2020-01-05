package com.example.project.service;

import com.example.project.model.Movie;
import com.example.project.repos.MovieRepos;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SearchMovieService {

    private final MovieRepos movieRepos;

    public List<Movie> findMoviesByTitle(String pattern, int page) {
        Pageable pageable = PageRequest.of(page, 10,
                Sort.by("popularity").descending().and(Sort.by("id")));
        return movieRepos.findByTitleContainsIgnoreCase(pattern, pageable);
    }
}
