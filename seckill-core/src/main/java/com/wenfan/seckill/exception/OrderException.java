package com.wenfan.seckill.exception;

/**
 * Created by wenfan on 2020/2/8 12:20
 */
public class OrderException extends Exception{

    private static final long serialVersionUID = 1L;

    protected String errorCode;
    protected String errorMsg;


    public OrderException() {
        super();
    }

    public OrderException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public OrderException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;

    }

    public OrderException(String errorMsg) {
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
