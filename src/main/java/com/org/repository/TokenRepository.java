package com.org.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findAllByUser_UsernameAndExpiredFalseAndRevokedFalse(String username);
    Optional<Token> findByToken(String token);
}
