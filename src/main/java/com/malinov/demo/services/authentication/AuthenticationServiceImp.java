package com.malinov.demo.services.authentication;


import com.malinov.demo.dto.AuthRequest;
import com.malinov.demo.dto.AuthenticationResponse;
import com.malinov.demo.enums.State;
import com.malinov.demo.exceptions.ResourceNotFoundException;
import com.malinov.demo.models.Role;
import com.malinov.demo.models.Users;
import com.malinov.demo.repositories.RoleRepository;
import com.malinov.demo.repositories.UsersRepository;
import com.malinov.demo.security.jwt.JwtService;
import com.malinov.demo.security.services.UserDetailsImpl;
import com.malinov.demo.services.token.TokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final TokenService tokenService;
    private final UsersRepository employeeRepository;

    @Override
    public AuthenticationResponse authenticate(AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtService.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            List<Role> roleList = new ArrayList<>();
            List<String> roleNames = Arrays.asList("SUPER_ADMIN", "USER", "ADMIN", "SUPERVISOR", "CASHIER",
                    "COURIER", "COMMERCIAL", "DISTRIBUTOR");

            for (String authority : roles) {
                if (roleNames.contains(authority)) {
                    roleList.add(getRoleByName(authority));
                }
            }

            tokenService.revokeAllUsersTokens(this.getAuthor());
            tokenService.save(this.getAuthor(), token);

            return new AuthenticationResponse(
                    userDetails.getId(),
                    userDetails.getFirstName(),
                    userDetails.getLastName(),
                    userDetails.getEmail(),
                    userDetails.getPhoneNumber(),
//                    roleList,
                    new ArrayList<>(),
                    userDetails.getCreatedAt(),
                    userDetails.getState(),
                    token
            );
        } catch (BadCredentialsException e) {
            throw new ResourceNotFoundException("Email ou mot de passe incorrect");
        }
    }

    @Override
    public Users getAuthor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            return employeeRepository.findByEmailAndState(email, State.ACTIVATED);
        }
        return null;
    }

        Role getRoleByName(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            throw new ResourceNotFoundException("Role incorrect");
        }
        return role;
    }
}
