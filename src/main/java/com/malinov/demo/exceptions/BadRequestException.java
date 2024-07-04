package com.malinov.demo.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String me) {
        super(me);
    }
}

