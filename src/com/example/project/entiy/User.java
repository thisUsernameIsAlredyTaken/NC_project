package com.example.project.entiy;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "uzer")
@NoArgsConstructor
public class User implements Serializable {

    public interface CoreInfo extends Serializable {
        String getId();
        String getUsername();
        Date getRegisterDate();
    }

    @Data
    public static class Stat implements Serializable {
        private List<Genre> favoriteGenres;
    }

    @Id
    @Column(length = 11)
    private String id;

    @Column(nullable = false)
    private Date registerDate;

    @Column(nullable = false, unique = true, length = 32)
    private String username;

    @OneToMany(mappedBy = "user")
    private List<PlannedMovie> plannedMovies;

    @OneToMany(mappedBy = "user")
    private List<WatchedMovie> watchedMovies;
}
