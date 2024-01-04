package com.atguigu.spzx.common.exp;

import com.atguigu.spzx.model.vo.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 捕获处理Exception类型的异常
    @ExceptionHandler(value = Exception.class)
    public Result handleException(Exception exception){
        // 200 成功 201 失败
        return Result.build(null,201,exception.getMessage());
    }
    // 捕获处理ArithmeticException类型的异常
    @ExceptionHandler(value = ArithmeticException.class)
    public Result handleException(ArithmeticException exception){
        // 200 成功 201 失败
        return Result.build(null,201,exception.getMessage());
    }
    // 捕获处理自定义类型的异常
    @ExceptionHandler(value = SpzxGuiguException.class)
    public Result handleSpzxGuiguException(SpzxGuiguException exception){
        // 200 成功 201 失败
        return Result.build(null,exception.getCode(),exception.getMessage());
    }
}
