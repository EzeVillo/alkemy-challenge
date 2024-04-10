package com.villo.alkemychallenge.repositories;

import com.villo.alkemychallenge.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    boolean existsByTitle(String title);

    boolean existsById(@NonNull Long id);

    boolean existsByImage(String image);

    Optional<Movie> findByImage(String image);

    @NonNull
    Movie save(@NonNull Movie movie);
}
