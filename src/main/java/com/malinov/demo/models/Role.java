package com.malinov.demo.models;

import com.malinov.demo.enums.State;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany
    @ToString.Exclude
    private List<Users> users = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private State state = State.ACTIVATED;

    public Role(String name) {
        this.name = name;
    }
}
