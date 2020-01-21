package com.example.project.repository;

import com.example.project.entiy.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepo extends JpaRepository<Movie, String> {

    @Query("select m from Movie m where lower(m.title) like :pattern")
    List<Movie> search(@Param("pattern") String pattern, Pageable pageable);

    List<Movie> findByTitleContainsIgnoreCase(String pattern, Pageable pageable);


    @Query(nativeQuery = true, value = "" +
            "select m.* " +
            "    from ( " +
            "            select m.id, count(*) c from movie m " +
            "            join movie_genres mg on m.id = mg.movie_id " +
            "            join genre g on mg.genres_id = g.id " +
            "            where m.id not in ( " +
            "                select wm.movie_id from watched_movie wm " +
            "                where wm.user_id = :id " +
            "            ) " +
            "            and mg.genres_id in ( " +
            "                select g.id from watched_movie wm " +
            "                join movie m on wm.movie_id = m.id " +
            "                join movie_genres mg on m.id = mg.movie_id " +
            "                join genre g on mg.genres_id = g.id " +
            "                    where wm.user_id = :id " +
            "                    group by g.id " +
            "                    order by sum(wm.rating) desc " +
            "                    limit 3 " +
            "            ) " +
            "        group by m.id " +
            "    ) t join movie m on t.id = m.id " +
            "    order by (t.c * m.rating * m.popularity) desc " +
            "    limit 10 " +
            "")
    List<Movie> getRecommendByUserId(@Param("id") String userId);

    @Query(nativeQuery = true, value = "" +
            "select * from movie m " +
            "order by (m.rating * m.popularity) desc limit 10")
    List<Movie> getDefaultRecommend();
}
