package com.wenfan.seckill.validate.sms.controller;

import com.wenfan.seckill.entity.SysUser;
import com.wenfan.seckill.mapper.SysUserMapper;
import com.wenfan.seckill.utils.InputValidator;
import com.wenfan.seckill.utils.StringUtils;
import com.wenfan.seckill.validate.sms.SmsCodeSender;
import com.wenfan.seckill.vo.ResponseInfo;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by wenfan on 2020/1/27 12:43
 */
@RestController
public class SmsController {

    @Autowired
    private SmsCodeSender smsCodeSender;

    @Autowired
    private SysUserMapper userMapper;

    @GetMapping("/sms")
    public ResponseInfo sendMobileMessage(HttpServletRequest request){
        String mobile = request.getParameter("phone");
        String smsCode = "";
        SysUser user ;
        if (InputValidator.isMobile(mobile)) {
            user = userMapper.getSysUserByUsername(mobile);
            if (user != null )
                return new ResponseInfo("400","该用户已注册");
            HttpSession session = request.getSession();
            smsCode = RandomStringUtils.randomNumeric(4);
            smsCodeSender.send(mobile,smsCode);
            session.setAttribute("phoneAndCode", mobile+"-"+smsCode);
        }
        return ResponseInfo.success(smsCode);
    }
}
