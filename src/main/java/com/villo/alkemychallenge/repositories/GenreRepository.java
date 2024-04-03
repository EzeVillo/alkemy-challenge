package com.villo.alkemychallenge.repositories;

import com.villo.alkemychallenge.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    boolean existsByName(String name);

    boolean existsById(@NonNull Long id);

    boolean existsByImage(String image);

    Optional<Genre> findByImage(String image);

    @NonNull
    Genre save(@NonNull Genre genre);
}
