package com.villo.alkemychallenge.repositories;

import com.villo.alkemychallenge.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    boolean existsByName(String name);

    boolean existsById(@NonNull Long id);
}
