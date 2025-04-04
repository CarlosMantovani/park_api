package com.mantovani.park_api.exception;

import lombok.Getter;

@Getter
public class UsernameUniqueViolationException extends RuntimeException {

    private String username;

    public UsernameUniqueViolationException(String username) {
        this.username = username;
    }
}
