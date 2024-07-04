package com.malinov.demo.security.services;

import com.malinov.demo.enums.State;
import com.malinov.demo.exceptions.ResourceNotFoundException;
import com.malinov.demo.models.Users;
import com.malinov.demo.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = repository.findByEmailAndState(email, State.ACTIVATED);
        if (users == null){
            throw new ResourceNotFoundException("Email ou mot de passe incorrect");
        }
        return UserDetailsImpl.build(users);
    }
}
