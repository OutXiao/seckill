package com.wenfan.seckill.exception;

import com.wenfan.seckill.vo.ResponseInfo;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;

/**
 * Created by wenfan on 2019/12/30 20:53
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    private Logger log = LoggerFactory.getLogger(getClass());


    // 处理非法参数异常
    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseInfo badRequestExceptionHandler(IllegalArgumentException e) {
        return new ResponseInfo(HttpStatus.BAD_REQUEST + "", e.getMessage());
    }

    // 拒绝访问异常处理
    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseInfo badRequestExceptionHandler(AccessDeniedException e) {
        return new ResponseInfo(HttpStatus.FORBIDDEN + "", e.getMessage());
    }


    @ExceptionHandler({MissingServletRequestParameterException.class, HttpMessageNotReadableException.class,
            UnsatisfiedServletRequestParameterException.class, MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseInfo badRequestExceptionHandler(Exception e) {
        return new ResponseInfo(HttpStatus.BAD_REQUEST + "", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseInfo badRequestExceptionHandler(SignatureException e) {
        return new ResponseInfo(HttpStatus.FORBIDDEN + "", e.getMessage());
    }


    // 处理 Sys
    @ExceptionHandler(SystemException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseInfo badRequestExceptionHandler(SystemException t) {
        return new ResponseInfo(HttpStatus.INTERNAL_SERVER_ERROR + "", t.getMessage());
    }

    // 处理 Throwable
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseInfo badRequestExceptionHandler(Throwable t) {
        return new ResponseInfo(HttpStatus.INTERNAL_SERVER_ERROR + "", t.getMessage());
    }


    // 处理 orderException
    @ExceptionHandler(OrderException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseInfo badRequestExceptionHandler(OrderException t) {
        return new ResponseInfo(HttpStatus.INTERNAL_SERVER_ERROR + "", t.getMessage());
    }

}
