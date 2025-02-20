package com.ddiv.juejin_fk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiResult<T> {
    private Integer code;
    private String message;
    private T data;


    public static <T> ApiResult<T> success(Integer code, String message) {
        return new ApiResult(code, message, null);
    }

    public static <T> ApiResult<T> success(Integer code, String message, T data) {
        return new ApiResult(code, message, data);
    }


    public static <T> ApiResult<T> error(Integer code, String message) {
        return new ApiResult(code, message, null);
    }

    public static <T> ApiResult<T> error(Integer code, String message, T data) {
        return new ApiResult(code, message, data);
    }

}
