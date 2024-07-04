package com.malinov.demo.services.token;

import com.malinov.demo.models.Token;
import com.malinov.demo.models.Users;
import com.malinov.demo.repositories.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository repository;

    @Override
    public Token save(Users users, String token) {
        Token savedToken = Token.builder()
                .token(token)
                .revoked(false)
                .expired(false)
                .createdAt(LocalDateTime.now())
                .users(users)
                .build();
        return repository.save(savedToken);
    }

    @Override
    public void revokeAllUsersTokens(Users users) {
        List<Token> tokens = repository.findAllByUsersIdAndExpiredIsFalseOrRevokedIsFalse(users.getId());
        if (tokens.isEmpty()) {
            return;
        }
        tokens.forEach(token -> {
            if (token.getUsers().equals(users)) {
                token.setRevoked(true);
                token.setExpired(true);
                token.setLogoutAt(LocalDateTime.now());
            }
        });
        repository.saveAll(tokens);
    }

}
