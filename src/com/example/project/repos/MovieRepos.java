package com.example.project.repos;


import com.example.project.model.Movie;
import com.example.project.model.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MovieRepos extends JpaRepository<Movie, String> {

    int MAX_ID_LENGTH = 11;

    default int getMaxIdLength() {
        return MAX_ID_LENGTH;
    }

    List<Movie> findByTitleContainsIgnoreCase(String pattern, Pageable pageable);

    List<Movie> findByTypeIn(Collection<Type> types, Pageable pageable);

    @Query(nativeQuery = true,
            value = "select * from movie m " +
                    "join type t on t.id = m.type_id " +
                    "join movie_genres mg on m.id = mg.movie_id " +
                    "join genre g on mg.genres_id = g.id " +
                    "where m.type_id in :types")
    List<Movie> findByTypeIn2(@Param("types") Collection<Type> types, Pageable pageable);

    @Query(nativeQuery = true,
            value = "select void_func(array[ :ints ])")
    int callTest(@Param("ints") Collection<Integer> ints);

//    @Query(value = "select m from Movie m where m.id = :id")
    @Query(nativeQuery = true,
            value = "select * from get_mov(:id)")
    List<Movie> byId(@Param("id") String id);

    @Query(nativeQuery = true,
            value = "select * " +
                    "   from get_recommended(:typeIds, :genreIds) r " +
                    "       where r.id not in :watched ")
    List<Movie> getRecommended(@Param("typeIds") String typeIds,
                               @Param("genreIds") String genreIds,
                               @Param("watched") Collection<String> watchedMovieIds,
                               Pageable pageable);

//    @Query(
//            nativeQuery = true,
//            value = "SELECT m.id, m.title, m.start_year, m.end_year, m.runtime, m.type_id, m.popularity" +
//                    "   FROM movie m" +
//                    "   WHERE m.type_id IN :typeIds AND" +
//                    "       "
//    )
//    List<Movie> findByTypeAndGenres(
//            @Param("typeIds") Set<Integer> typeIds,
//            @Param("genreIds") Set<Integer> genreIds
//    );
}
