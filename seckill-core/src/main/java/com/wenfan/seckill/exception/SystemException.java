package com.wenfan.seckill.exception;

import com.wenfan.seckill.rest.RestMsg;

/**
 * Created by wenfan on 2020/1/17 19:10
 */
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected String errorCode;
    protected String errorMsg;


    public SystemException() {
        super();
    }



    public SystemException(RestMsg restMsg){
        super(restMsg.getMessage());
        this.errorCode = restMsg.getCode().toString();
        this.errorMsg = restMsg.getMessage();
    }

    public SystemException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public SystemException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;

    }

    public SystemException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
