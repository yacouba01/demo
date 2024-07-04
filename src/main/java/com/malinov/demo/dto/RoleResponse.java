package com.malinov.demo.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RoleResponse {
    private Long id;
    private String name;
    private String description;
    private List<UsersResponse> users;
}
