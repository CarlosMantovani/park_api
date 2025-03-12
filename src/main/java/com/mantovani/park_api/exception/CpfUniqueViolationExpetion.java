package com.mantovani.park_api.exception;

public class CpfUniqueViolationExpetion extends RuntimeException {
    public CpfUniqueViolationExpetion(String message){
        super(message);
    }
}
