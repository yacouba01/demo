package com.malinov.demo.dto;

import com.malinov.demo.enums.State;
import com.malinov.demo.models.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter @Setter
public class UsersResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private List<Role> roles;
    private State state;
}
