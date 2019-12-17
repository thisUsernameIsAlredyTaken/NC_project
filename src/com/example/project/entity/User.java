package com.example.project.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;


@Data
@NoArgsConstructor
@Entity
@Table(name = "uzer")
public class User {

    @Id
    private String id;

    @Column(nullable = false)
    private String username;

    @OneToMany
    private Set<ListedMovie> movies;
}
