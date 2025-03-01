package com.ddiv.juejin_fk.exception;

import com.ddiv.juejin_fk.pojo.ApiResult;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @Autowired
    HttpServletResponse response;

    @ExceptionHandler(UserNotFoundException.class)
    public ApiResult<Object> handleUserNotFoundException(UserNotFoundException e) {
        response.setStatus(e.getCode());
        return ApiResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(LoginException.class)
    public ApiResult<Object> handleLoginException(LoginException e) {
        response.setStatus(e.getCode());
        return ApiResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ApiResult<Object> handleUnauthorizedException(UnauthorizedException e) {
        response.setStatus(e.getCode());
        return ApiResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResult<Object> exceptionHandler(Exception e) {
        response.setStatus(500);
        return ApiResult.error(500, "服务器异常");
    }
}
