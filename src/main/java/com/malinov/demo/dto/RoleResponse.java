package com.malinov.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RoleResponse {
    private Long id;
    private String name;
}
