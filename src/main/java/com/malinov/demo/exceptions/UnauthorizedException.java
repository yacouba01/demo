package com.malinov.demo.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

@ToString
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UnauthorizedException extends RuntimeException {

    private HttpStatus status = HttpStatus.UNAUTHORIZED;
    private String message;

}

