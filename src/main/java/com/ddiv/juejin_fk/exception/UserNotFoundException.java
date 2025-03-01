package com.ddiv.juejin_fk.exception;


import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private int code = 404;

    public UserNotFoundException(int code, String message) {
        super(message);
        this.code = code;
    }

    public UserNotFoundException(String message) {
        super(message);
    }

}
