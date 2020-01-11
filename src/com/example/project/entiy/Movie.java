package com.example.project.entiy;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Movie implements Serializable {

    public static final int MAX_ID_LENGTH = 11;
    public static final int MAX_TITLE_LENGTH = 256;

    @Id
    @Column(length = MAX_ID_LENGTH)
    private String id;

    @Column(length = MAX_TITLE_LENGTH, nullable = false)
    private String title;

    private int startYear;

    private Integer endYear;

    private int runtime;

    @ManyToOne(optional = false)
    private Type type;

    @ManyToMany
    private List<Genre> genres;

    private int popularity;

    private double rating;
}
