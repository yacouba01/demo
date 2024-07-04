package com.malinov.demo.controllers;


import com.malinov.demo.dto.AuthRequest;
import com.malinov.demo.dto.AuthenticationResponse;
import com.malinov.demo.services.authentication.AuthenticationService;
import com.malinov.demo.services.logout.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService service;
    private final LogoutService logoutService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    AuthenticationResponse authenticate(@RequestBody AuthRequest request) {
        return service.authenticate(request);
    }

    @GetMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logoutService.logout(request, response, authentication);
    }
}