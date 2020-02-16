package com.wenfan.seckill.controller;

import com.wenfan.seckill.dto.SysUserDto;
import com.wenfan.seckill.entity.SysUser;
import com.wenfan.seckill.service.UserService;
import com.wenfan.seckill.utils.StringUtils;
import com.wenfan.seckill.vo.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wenfan on 2020/1/27 10:44
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 手机验证码 过滤器端已经校验
     * @param request
     * @return
     */
    @PostMapping("/register")
    public ResponseInfo register(HttpServletRequest request){
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        SysUser sysUser = null;
        if (!StringUtils.isAllEmptys(phone,password)){
            sysUser = userService.addSysUser(new SysUserDto(phone,password));
        }
        return ResponseInfo.success(sysUser);

    }

}
