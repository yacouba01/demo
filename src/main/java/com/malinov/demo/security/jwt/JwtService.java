package com.malinov.demo.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.malinov.demo.exceptions.TokenExpiredException;
import com.malinov.demo.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

import static com.malinov.demo.security.SecurityConstants.*;


@Service
public class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);
    public String extractUsername(String token) {
        return extractClaim(token, Claim::asString);
    }

    public <T> T extractClaim(String token, Function<Claim, T> claimsResolver) {
        final DecodedJWT decodedJWT = JWT.decode(token);
        final Claim claim = decodedJWT.getClaim("sub");
        return claimsResolver.apply(claim);
    }

    public String generateJwtToken(Authentication authentication) {
        String username = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();

        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET_KEY);
        return JWT.create()
                .withIssuer(GET_COMPANY_NAME)
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        try {
            // Decoder le token
            final DecodedJWT decodedJWT = JWT.decode(token);

            // Récupérer la date expiration du token depuis le token decoder
            final Date expirationDate = decodedJWT.getExpiresAt();

            // Check if the token has expired
            if (expirationDate != null && expirationDate.before(new Date())) {
                // Si le Token est expiré, lance TokenExpiredException
                throw new TokenExpiredException("Le token d'authentification a expiré.");
            }

            // Si le Token est expiré ou non, renvoie true
            return false;
        } catch (TokenExpiredException e) {
            log.error("Token: " + e.getMessage());
            return true;
        }
    }

}

