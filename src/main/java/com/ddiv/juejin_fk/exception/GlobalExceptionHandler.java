package com.ddiv.juejin_fk.exception;

import com.ddiv.juejin_fk.pojo.ApiResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public <T> ApiResult<T> exceptionHandler(Exception e) {
        return ApiResult.error(500, "服务器异常");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ApiResult<UserNotFoundException> exceptionHandler(UserNotFoundException e) {
        return ApiResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ApiResult<NotAllowedRequest> exceptionHandler(NotAllowedRequest e) {
        return ApiResult.error(e.getCode(), e.getMessage());
    }
}
