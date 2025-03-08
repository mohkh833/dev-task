package com.project.movies.movie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovieRepository extends JpaRepository<Movie, Integer>, JpaSpecificationExecutor {
    @Query("""
            SELECT movie
            FROM Movie movie
            """)
    Page<Movie> findAllDisplayableMovies(Pageable pageable, Integer userId);

    @Query("SELECT m FROM Movie m WHERE " +
            "(LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Movie> searchMovies(@Param("keyword") String keyword, Pageable pageable);


}
