package com.wenfan.seckill.vo;

import com.wenfan.seckill.rest.RestMsg;

import java.io.Serializable;
import java.util.List;


public class ResponseInfo<T> implements Serializable {

    private static final long serialVersionUID = -4417715614021482064L;

    private String code;
    private String message;

    private List<T> data;

    private Object object;

    public ResponseInfo(String code, String message, List<T> data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseInfo(RestMsg msg, List<T> data) {
        super();
        this.code = msg.getCode().toString();
        this.message = msg.getMessage();
        this.data = data;
    }

    public ResponseInfo(RestMsg restMsg){
        this.code = restMsg.getCode().toString();
        this.message = restMsg.getMessage();
    }

    public ResponseInfo(RestMsg restMsg,Object returnObject){
        this.code = restMsg.getCode().toString();
        this.message = restMsg.getMessage();
        this.object = returnObject;
    }

    public ResponseInfo(String code, String message, Object data) {
        super();
        this.code = code;
        this.message = message;
        this.object = data;
    }

    public ResponseInfo(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }




    public static ResponseInfo success() {
        return new ResponseInfo(RestMsg.SUCCESS);
    }




    public static ResponseInfo success(Object o) {
        return new ResponseInfo(RestMsg.SUCCESS, o);
    }

    public static ResponseInfo exception(String exceptionMsg){
        return new ResponseInfo("500",exceptionMsg);
    }

    public static ResponseInfo fail() {
        return new ResponseInfo(RestMsg.FAILURE);
    }


    public static ResponseInfo restMsg(RestMsg restMsg){
        return new ResponseInfo(restMsg);
    }



    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
