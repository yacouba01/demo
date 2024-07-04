package com.malinov.demo.services.authentication;


import com.malinov.demo.dto.AuthRequest;
import com.malinov.demo.dto.AuthenticationResponse;
import com.malinov.demo.models.Users;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthRequest authRequest);
    Users getAuthor();
}
