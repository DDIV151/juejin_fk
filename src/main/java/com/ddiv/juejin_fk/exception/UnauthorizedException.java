package com.ddiv.juejin_fk.exception;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException {
    int code = 401;

    public UnauthorizedException(int code, String message) {
        super(message);
        this.code = code;
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
