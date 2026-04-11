package com.springproject.spring_boot_jwt.repositories;

import com.springproject.spring_boot_jwt.models.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(@NotBlank(message = "Username is required") String username);

    boolean existsByUsernameAndIdNot(@NotBlank(message = "Username is required") String username, Long id);
}
