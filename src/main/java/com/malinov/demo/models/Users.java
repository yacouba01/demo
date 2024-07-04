package com.malinov.demo.models;

import com.malinov.demo.enums.State;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDateTime createdAt = LocalDateTime.now();
    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Role> roles = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private State state = State.ACTIVATED;
}
