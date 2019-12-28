package com.example.project.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
                    generator = "sequence_genre")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;
}
