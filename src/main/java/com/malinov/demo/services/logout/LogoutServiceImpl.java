package com.malinov.demo.services.logout;

import com.malinov.demo.exceptions.TokenExpiredException;
import com.malinov.demo.models.Token;
import com.malinov.demo.repositories.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutHandler, LogoutService {

    private final TokenRepository repository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            Token storedToken = repository.findByTokenAndRevokedIsFalseOrExpiredIsFalse(jwt);
            if (storedToken != null) {
                storedToken.setExpired(true);
                storedToken.setRevoked(true);
                storedToken.setLogoutAt(LocalDateTime.now());
                repository.save(storedToken);
                SecurityContextHolder.clearContext();
            } else {
                throw new TokenExpiredException("Le token est invalide");
            }
        }
    }
}
