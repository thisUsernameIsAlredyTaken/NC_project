package com.example.project.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class ListedMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Movie movie;

    @Column(nullable = false)
    private Integer mark;

    @Column(nullable = false)
    private Date date;
}
