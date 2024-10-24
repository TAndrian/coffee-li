package com.project.coffee_li.exception;

import lombok.Getter;

@Getter
public abstract class GlobalException extends RuntimeException {

    private final String code;

    protected GlobalException(String message, String code) {
        super(message);
        this.code = code;
    }
}
