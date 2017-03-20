package com.irunninglog.api.security;

public final class AuthzException extends SecurityException {

    public AuthzException(String message) {
        super(message);
    }

}
