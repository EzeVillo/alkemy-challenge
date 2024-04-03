package com.villo.alkemychallenge.repositories;

import com.villo.alkemychallenge.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, Long>, JpaSpecificationExecutor<Film> {
    boolean existsByTitle(String title);

    boolean existsById(@NonNull Long id);

    boolean existsByImage(String image);

    Optional<Film> findByImage(String image);

    @NonNull
    Film save(@NonNull Film film);
}
