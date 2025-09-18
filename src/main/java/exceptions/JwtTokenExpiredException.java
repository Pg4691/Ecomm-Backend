package com.ecomm.firstproject.exceptions;

public class JwtTokenExpiredException extends RuntimeException {
    public JwtTokenExpiredException() {
        super("JWT token has expired. Please login again.");
    }
}
