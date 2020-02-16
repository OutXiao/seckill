package com.wenfan.seckill.controller;

import com.wenfan.seckill.vo.ResponseInfo;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * Created by wenfan on 2020/1/27 21:47
 */
//@RestController    // filter 异常处理？？？？
public class ErrorController extends BasicErrorController {

    public ErrorController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = getStatus(request);
        //自定义的错误信息类
        //status.value():错误代码，
        //body.get("message").toString()错误信息
        return new ResponseEntity<>(body, status);
    }

    @Override
    public String getErrorPath() {
        return "error/error";
    }

}
