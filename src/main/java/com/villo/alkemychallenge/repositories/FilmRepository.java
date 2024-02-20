package com.villo.alkemychallenge.repositories;

import com.villo.alkemychallenge.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {
}
