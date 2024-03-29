package com.villo.alkemychallenge.repositories;

import com.villo.alkemychallenge.entities.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long>, JpaSpecificationExecutor<Character> {
}
