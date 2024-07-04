package com.malinov.demo.security.services;

import com.malinov.demo.enums.State;
import com.malinov.demo.models.Users;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private LocalDateTime createdAt;
    private State state;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String firstName, String lastName, String email,
                           String phoneNumber, String password, LocalDateTime createdAt, State state,
                           Collection<? extends GrantedAuthority> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.createdAt = createdAt;
        this.state = state;
        this.authorities = roles;
    }

    public static UserDetailsImpl build(Users employee){
        List<GrantedAuthority> roles = employee.getRoles().stream()
                .map(appRole -> new SimpleGrantedAuthority(appRole.getName()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getPassword(),
                employee.getCreatedAt(),
                employee.getState(),
                roles
        );
    }

    public static UserDetailsImpl build(Long id, String firstName, String lastName, String email,
                                        String phoneNumber, String password,
                                        LocalDateTime createdAt, State state,
                                        Collection<? extends GrantedAuthority> roles) {
        return new UserDetailsImpl(id, firstName, lastName, email,
                 phoneNumber, password, createdAt, state, roles);
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}