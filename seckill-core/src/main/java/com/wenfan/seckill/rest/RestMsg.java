package com.wenfan.seckill.rest;

/**
 * @author:wenfan
 * @description:
 * @data: 2019/2/27 17:03
 */
public enum RestMsg {

    SUCCESS(200, "操作成功"),

    FAILURE(500, "操作失败"),

    OK(200, "OK"),

    INNER_ERROR(500, "内部服务器错误"),

    BAD_REQUEST(400, "没有该资源"),

    ERROR_PARAMETER(401, "错误参数"),

    LOGIN_TOKEN_NOT_EXIST(403, "登录过期或者失效"),

    Not_LOGIN(403, "请登录"),

    EDIT_FAILURE(500,"修改失败"),

    EDIT_SUCCESS(200,"修改成功"),

    PASSWORD_INCORRECT(403,"密码错误");


    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    RestMsg(Integer code, String messgage) {
        this.code = code;
        this.message = messgage;
    }

    public static Integer getCode(String name) {
        for (RestMsg restMsg : RestMsg.values()) {
            if (restMsg.getMessage().equals(name)) {
                return restMsg.code;
            }
        }
        return null;
    }


    public static String getMessage(Integer id) {
        for (RestMsg resultCode : RestMsg.values()) {
            if (resultCode.getCode().equals(id)) {
                return resultCode.message;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
