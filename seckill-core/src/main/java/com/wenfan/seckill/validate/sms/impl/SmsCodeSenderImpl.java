package com.wenfan.seckill.validate.sms.impl;

import com.wenfan.seckill.validate.sms.SmsCodeSender;
import org.springframework.stereotype.Service;

/**
 * Created by wenfan on 2020/1/27 12:56
 */
@Service
public class SmsCodeSenderImpl implements SmsCodeSender {

    @Override
    public void send(String mobile, String code) {
        System.out.println("向"+mobile+"发送"+code);
    }
}
