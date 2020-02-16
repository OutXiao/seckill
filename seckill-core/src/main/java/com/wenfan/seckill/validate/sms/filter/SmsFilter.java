package com.wenfan.seckill.validate.sms.filter;

import com.wenfan.seckill.utils.ResponseUtil;
import com.wenfan.seckill.vo.ResponseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wenfan on 2020/1/27 12:21
 */
@Component
public class SmsFilter extends OncePerRequestFilter {


    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (httpServletRequest.getRequestURI().equals("/register")){
            String smsCode = httpServletRequest.getParameter("code");
            String phone = httpServletRequest.getParameter("phone");
            String phoneAndcodeOnsession = (String) httpServletRequest.getSession().getAttribute("phoneAndCode");
            String phoneOnSession = phoneAndcodeOnsession.split("-")[0];
            String codeOnSession = phoneAndcodeOnsession.split("-")[1];
            if (smsCode.equals(codeOnSession) && phone.equals(phoneOnSession)) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
            else{
                ResponseInfo info = new ResponseInfo("400", "验证码错误");
                ResponseUtil.responseJson(httpServletResponse, HttpStatus.UNAUTHORIZED.value(), info);
            }
        }else {
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }

    }
}
