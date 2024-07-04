package com.malinov.demo.dto;


import com.malinov.demo.enums.State;
import com.malinov.demo.models.Role;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AuthenticationResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private List<Role> roles;
    private LocalDateTime createdAt;
    private State state;
    private String accessToken;
}
