package com.example.project.entiy;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Movie implements Serializable {

    @Id
    @Column(length = 11)
    private String id;

    @Column(length = 511)
    private String title;

    private int startYear;

    private int endYear;

    private int runtime;

    @ManyToOne
    private Type type;

    @ManyToMany
    private List<Genre> genres;

    private int popularity;
}
