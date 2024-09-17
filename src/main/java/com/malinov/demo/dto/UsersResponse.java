package com.malinov.demo.dto;

import com.malinov.demo.enums.State;
import com.malinov.demo.models.Role;
import lombok.*;

import java.util.List;

@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UsersResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private List<RoleResponse> roles;
    private State state;
}
