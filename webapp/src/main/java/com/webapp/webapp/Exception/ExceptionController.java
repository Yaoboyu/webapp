package com.webapp.webapp.Exception;

import com.webapp.webapp.Pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常捕获器
 */
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    public Result ex(Exception ex){
        ex.printStackTrace();
        return Result.error(ex.getMessage());
    }
}
