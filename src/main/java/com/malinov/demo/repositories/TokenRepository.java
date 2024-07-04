package com.malinov.demo.repositories;

import com.malinov.demo.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String> {
    Optional<Token> findByToken(String jwt);
    List<Token> findAllByUsersIdAndExpiredIsFalseOrRevokedIsFalse(Long id);
    Token findByTokenAndRevokedIsFalseOrExpiredIsFalse(String jwt);
}
