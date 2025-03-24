package com.mantovani.park_api.exception;

import lombok.Getter;

@Getter
public class CpfUniqueViolationException extends RuntimeException {

    private String cpf;
    public CpfUniqueViolationException(String cpf) {
        this.cpf = cpf;
    }
}