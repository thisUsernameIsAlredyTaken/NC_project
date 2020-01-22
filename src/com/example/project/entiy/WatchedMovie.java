package com.example.project.entiy;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class WatchedMovie {

    public interface NoUser extends Serializable {
        Long getId();
        Date getDate();
        Movie getMovie();
        Integer getRating();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date date;

    @ManyToOne(optional = false)
    private Movie movie;

    @ManyToOne(optional = false)
    private User user;

    private Integer rating;
}
