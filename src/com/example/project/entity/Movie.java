package com.example.project.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Movie {

    @Id
    private String id;

    @Column(nullable = false)
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
}
