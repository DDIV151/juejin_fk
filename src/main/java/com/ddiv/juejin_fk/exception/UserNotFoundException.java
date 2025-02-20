package com.ddiv.juejin_fk.exception;

import lombok.*;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class UserNotFoundException extends RuntimeException{
    private Integer code;
    private String message;
}
