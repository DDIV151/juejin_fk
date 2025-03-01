package com.ddiv.juejin_fk.exception;

import lombok.Getter;

@Getter
public class LoginException extends RuntimeException {
    private int code = 401;

    public LoginException(int code, String message) {
        super(message);
        this.code = code;
    }

    public LoginException(String message) {
        super(message);
    }
}
