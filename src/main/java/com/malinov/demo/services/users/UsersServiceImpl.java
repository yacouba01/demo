package com.malinov.demo.services.users;

import com.malinov.demo.dto.RoleResponse;
import com.malinov.demo.dto.UsersResponse;
import com.malinov.demo.enums.State;
import com.malinov.demo.exceptions.AlreadyExistException;
import com.malinov.demo.exceptions.ResourceNotFoundException;
import com.malinov.demo.models.Users;
import com.malinov.demo.repositories.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    @Override
    public UsersResponse save(Users users) {
        Users phoneNumber = usersRepository.findByPhoneNumber(users.getPhoneNumber());
        if (phoneNumber != null) {
            throw new AlreadyExistException("Le numéro de téléphone est déjà utilisé.");
        }
        return mapToResponse(usersRepository.save(users));
    }

    @Override
    public UsersResponse findByIdAndState(Long id, State state) {
        Users users = usersRepository.findByIdAndState(id, state);
        if (users == null) {
            throw new ResourceNotFoundException("Cet utilisateur n'existe pas.");
        }
        return mapToResponse(users);
    }

    @Override
    public List<UsersResponse> findByState(State state) {
        List<Users> users = usersRepository.findByState(state);
        users.sort(Comparator.comparing(Users::getId).reversed());
        return mapToResponse(users);
    }

    @Override
    public List<UsersResponse> findByStateNot(State state) {
        List<Users> users = usersRepository.findByStateNot(state);
        users.sort(Comparator.comparing(Users::getId).reversed());
        return mapToResponse(users);
    }

    @Override
    public UsersResponse update(Users users) {
        Users userToUpdate = usersRepository.findByIdAndStateNot(users.getId(), State.ACTIVATED);
        if (userToUpdate == null) {
            throw new ResourceNotFoundException("Cet utilisateur n'existe pas.");
        }
        userToUpdate.setFirstName(users.getFirstName());
        userToUpdate.setLastName(users.getLastName());
        userToUpdate.setEmail(users.getEmail());
        userToUpdate.setPassword(users.getPassword());
        userToUpdate.setPhoneNumber(users.getPhoneNumber());
        return mapToResponse(usersRepository.save(userToUpdate));
    }

    @Override
    public void delete(Long id) {
        Users users = usersRepository.findByIdAndStateNot(id, State.DELETED);
        if (users == null) {
            throw new ResourceNotFoundException("Cet utilisateur n'existe pas.");
        }
        users.setState(State.DELETED);
    }

    @Override
    public UsersResponse activate(Users users) {
        Users userToActivate = usersRepository.findByIdAndStateNot(users.getId(), State.DELETED);
        if (userToActivate == null) {
            throw new ResourceNotFoundException("Cet utilisateur n'existe pas.");
        }
        userToActivate.setState(State.ACTIVATED);
        return mapToResponse(usersRepository.save(userToActivate));
    }

    @Override
    public UsersResponse mapToResponse(Users users) {
        List<RoleResponse> roles = new ArrayList<>();
        if (users.getRoles() != null && !users.getRoles().isEmpty()) {
            roles = users.getRoles().stream()
                    .map(role -> RoleResponse.builder()
                                    .id(role.getId())
                                    .name(role.getName())
                                    .build()).toList();
        }
        return UsersResponse.builder()
                .id(users.getId())
                .firstName(users.getFirstName())
                .lastName(users.getLastName())
                .email(users.getEmail())
                .phoneNumber(users.getPhoneNumber())
                .roles(roles)
                .state(users.getState())
                .build();
    }

    @Override
    public List<UsersResponse> mapToResponse(List<Users> users) {
        List<UsersResponse> usersResponse = new ArrayList<>();
        for (Users user : users) {
            usersResponse.add(mapToResponse(user));
        }
        return usersResponse;
    }
}
