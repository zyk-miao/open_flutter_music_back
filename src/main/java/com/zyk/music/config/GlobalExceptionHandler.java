package com.zyk.music.config;


import cn.hutool.jwt.JWTException;
import com.zyk.music.base.ReturnRes;
import com.zyk.music.exception.TokenUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public ReturnRes<Object> handlerException(Exception e) {
//        log.error(e.toString());
//        return ReturnRes.error("发生错误");
//    }

    @ExceptionHandler(TokenUnavailableException.class)
    public ReturnRes<Object> handlerTokenUnavailable(Exception e) {
        log.error(e.toString());
        return ReturnRes.refuse();
    }

    @ExceptionHandler(JWTException.class)
    public ReturnRes<Object> handerJWTException(Exception e){
        log.error(e.toString());
        return ReturnRes.refuse();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ReturnRes<Object> handerHttpRequestMethodNotSupportedException(Exception e){
        log.error(e.toString());
        return ReturnRes.refuse();
    }
}
