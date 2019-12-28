package com.example.project.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Data
@NoArgsConstructor
@Entity
public class ListedMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
                    generator = "sequence_listed_movie")
    private Long id;

    @ManyToOne
    private Movie movie;

    private Integer mark;

    @Column(nullable = false)
    private Date date;
}
