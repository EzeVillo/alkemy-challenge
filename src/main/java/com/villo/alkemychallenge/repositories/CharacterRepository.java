package com.villo.alkemychallenge.repositories;

import com.villo.alkemychallenge.entities.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long>, JpaSpecificationExecutor<Character> {
    boolean existsByName(String name);

    boolean existsById(@NonNull Long id);

    boolean existsByImage(String image);

    Optional<Character> findByImage(String image);

    @NonNull
    Character save(@NonNull Character character);
}
