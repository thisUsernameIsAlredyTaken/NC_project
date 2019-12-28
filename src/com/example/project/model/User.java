package com.example.project.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;


@Data
@NoArgsConstructor
@Entity
@Table(name = "uzer")
public class User {

    @Id
    @Column(length = 11)
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @OneToMany
    private Set<ListedMovie> listedMovies;

    public List<Movie> planned() {
        List<Movie> result = new ArrayList<>();
        this.listedMovies.forEach(listedMovie -> {
            if (listedMovie.getMark() == null) {
                result.add(listedMovie.getMovie());
            }
        });
        return result;
    }

    public List<Movie> watched() {
        List<ListedMovie> lm = new ArrayList<>();
        List<Movie> result = new ArrayList<>();
        this.listedMovies.forEach(listedMovie -> {
            if (listedMovie.getMark() != null && listedMovie.getMark() >= 0) {
                lm.add(listedMovie);
            }
        });
        lm.sort((o1, o2) -> {
            if (o1.getMark().equals(o2.getMark())) {
                return o1.getMovie().getTitle().compareTo(o2.getMovie().getTitle());
            } else {
                return o2.getMark() - o1.getMark();
            }
        });
        lm.forEach(m -> {
            result.add(m.getMovie());
        });
        return result;
    }
}
