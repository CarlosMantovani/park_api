package com.mantovani.park_api.exception;

import lombok.Getter;

@Getter
public class CodigoUniqueViolationException extends RuntimeException {
    private String recurso;
    private String codigo;

    public CodigoUniqueViolationException(String message) {
        super(message);
    }

    public CodigoUniqueViolationException(String codigo, String recurso) {
        this.codigo = codigo;
        this.recurso = recurso;
    }
}
