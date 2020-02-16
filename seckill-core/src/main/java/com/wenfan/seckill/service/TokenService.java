package com.wenfan.seckill.service;

import com.wenfan.seckill.dto.Token;
import com.wenfan.seckill.vo.LoginUser;

/**
 * Created by wenfan on 2019/12/28 14:11
 */
public interface TokenService {

    Token saveToken(LoginUser user);

    void freshToken(LoginUser user);

    LoginUser getLoginUser(String token);

    boolean deleteToken(String token);

}
