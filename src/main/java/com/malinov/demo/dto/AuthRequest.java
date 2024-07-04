package com.malinov.demo.dto;

import lombok.*;

@Builder
@ToString
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AuthRequest {
    private String email;
    private String password;
    private String device;
    private String latitude;
    private String longitude;
}
