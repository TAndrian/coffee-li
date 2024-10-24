package com.project.coffee_li.exception;

public class NotFoundException extends GlobalException {
    public NotFoundException(String message, String code) {
        super(message, code);
    }
}
