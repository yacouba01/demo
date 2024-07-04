package com.malinov.demo.services.users;

import com.malinov.demo.dto.UsersResponse;
import com.malinov.demo.enums.State;
import com.malinov.demo.models.Users;

import java.util.List;

public interface UsersService {

    UsersResponse save(Users users);
    UsersResponse findByIdAndState(Long id, State state);
    List<UsersResponse> findByState(State state);
    List<UsersResponse> findByStateNot(State state);
    UsersResponse update(Users users);
    void delete(Long id);
    UsersResponse activate(Users users);
    UsersResponse mapToResponse(Users users);
    List<UsersResponse> mapToResponse(List<Users> users);
}
