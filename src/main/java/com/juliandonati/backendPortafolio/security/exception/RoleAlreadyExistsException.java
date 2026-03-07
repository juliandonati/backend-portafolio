package com.juliandonati.backendPortafolio.security.exception;

public class RoleAlreadyExistsException extends RuntimeException {
    public RoleAlreadyExistsException(String message) {
        super(message);
    }
}
