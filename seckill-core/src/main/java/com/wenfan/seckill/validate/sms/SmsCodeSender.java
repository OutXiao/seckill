package com.wenfan.seckill.validate.sms;

/**
 * Created by wenfan on 2020/1/27 12:42
 */
public interface SmsCodeSender {

    void send(String mobile,String code);

}
