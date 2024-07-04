package com.malinov.demo.repositories;

import com.malinov.demo.enums.State;
import com.malinov.demo.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByIdAndState(Long id, State state);
    Users findByIdAndStateNot(Long id, State state);
    List<Users> findByState(State state);
    List<Users> findByStateNot(State state);
    Users findByPhoneNumber(String phoneNumber);
}
