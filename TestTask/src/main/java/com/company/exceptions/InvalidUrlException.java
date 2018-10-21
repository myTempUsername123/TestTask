package com.company.exceptions;

public class InvalidUrlException extends RuntimeException {
    public InvalidUrlException(String s) {
        super(s);
    }
}
