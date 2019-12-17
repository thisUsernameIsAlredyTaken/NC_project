package com.example.project.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Type {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;
}
