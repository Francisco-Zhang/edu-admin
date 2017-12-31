package com.lesson.spring.web.support;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice   // advice : 建议或者增强
public class ExceptionHandlerController {

    // service层或者其他层有异常，一直向上抛，最终controler层就会捕获到异常然后交给这个类统一处理
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)    //有些前台不会根据 result:fail 判断结果失败，而是根据状态码，所以此处指定状态码(默认500)，方便前台判断
    public Map<String,Object>  handleException(RuntimeException e){

        Map<String,Object> result= new HashMap<>();
        result.put("result","fail");
        result.put("errMsg",e.getMessage());
        return  result;
    }
}
