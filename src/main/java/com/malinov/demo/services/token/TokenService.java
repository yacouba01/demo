package com.malinov.demo.services.token;


import com.malinov.demo.models.Token;
import com.malinov.demo.models.Users;

public interface TokenService {

    Token save(Users users, String token);
    void revokeAllUsersTokens(Users users);

}