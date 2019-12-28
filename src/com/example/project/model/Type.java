package com.example.project.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
                    generator = "sequence_type")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;
}
