package com.example.project.entiy;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.ManyToAny;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "uzer")
@NoArgsConstructor
public class User implements Serializable {

    public static final int MAX_USERNAME_LENGTH = 64;
    public static final int MAX_ID_LENGTH = 11;

    public interface Credentials extends Serializable {
        String getUsername();
        String getPassword();
        List<Role> getRoles();
    }

    public interface CoreInfo extends Serializable {
        String getId();
        String getUsername();
        Date getRegisterDate();
        List<Role> getRoles();
    }

    @Id
    @Column(length = MAX_ID_LENGTH)
    private String id;

    @Column(nullable = false)
    private Date registerDate;

    @Column(nullable = false, unique = true, length = MAX_USERNAME_LENGTH)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<PlannedMovie> plannedMovies;

    @OneToMany(mappedBy = "user")
    private List<WatchedMovie> watchedMovies;
}
