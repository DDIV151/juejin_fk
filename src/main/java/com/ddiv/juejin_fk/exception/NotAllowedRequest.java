package com.ddiv.juejin_fk.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotAllowedRequest extends RuntimeException {
    private Integer code;
    private String message;
}
