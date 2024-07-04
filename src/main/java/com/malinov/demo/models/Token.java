package com.malinov.demo.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@ToString
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token;
    private boolean revoked;
    private boolean expired;
    private LocalDateTime createdAt;
    private LocalDateTime logoutAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Users users;
}