package com.springproject.spring_boot_jwt.repositories;

import com.springproject.spring_boot_jwt.models.Token;
import com.springproject.spring_boot_jwt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {
    Optional<Token> findByToken(String token);

    List<Token> findAllByUserIdAndExpiredFalseAndRevokedFalse(Long id);





}
