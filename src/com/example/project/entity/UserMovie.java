package com.example.project.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@IdClass(UserMovie.UserMovieId.class)
public class UserMovie {

    @Data
    static class UserMovieId implements Serializable {
        private User user;
        private Movie movie;
    }

    @Id
    @ManyToOne
    private User user;

    @Id
    @ManyToOne
    private Movie movie;

    @Column(nullable = false)
    private Integer mark;

    @Column(nullable = false)
    private Date date;
}
