package com.example.project.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;


@Data
@NoArgsConstructor
@Entity
public class Movie {

    @Id
    @Column(length = 11)
    private String id;

    @Column(nullable = false, length = 511)
    private String title;

    @Column(nullable = false)
    private Integer startYear;

    private Integer endYear;

    @Column(nullable = false)
    private Integer runtime;

    @ManyToOne
    private Type type;

    @ManyToMany
    private Set<Genre> genres;

    @Column(nullable = false)
    private Integer popularity;
}
