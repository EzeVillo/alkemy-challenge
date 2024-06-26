package com.villo.alkemychallenge.modules.auth.repositories;

import com.villo.alkemychallenge.modules.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByMail(String mail);
}
