package com.wenfan.seckill.service.impl;

import com.wenfan.seckill.entity.SysPermission;
import com.wenfan.seckill.entity.SysUser;
import com.wenfan.seckill.mapper.SysPermissionMapper;
import com.wenfan.seckill.mapper.SysUserMapper;
import com.wenfan.seckill.service.TokenService;
import com.wenfan.seckill.vo.LoginUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by wenfan on 2019/12/28 15:10
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysPermissionMapper permissionMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser user = (LoginUser) redisTemplate.opsForValue().get("username:"+username);
        if (user == null){
            SysUser sysUser = userMapper.getSysUserByUsername(username);
            if (sysUser == null) {
                throw new AuthenticationCredentialsNotFoundException("用户名不存在");
            }
            List<SysPermission> permissions = permissionMapper.listPermissionsByUserId(sysUser.getId());
            LoginUser loginUser = new LoginUser();
            BeanUtils.copyProperties(sysUser, loginUser);
            loginUser.setPermissions(permissions);
            tokenService.freshToken(loginUser);
            return loginUser;
        }
        return user;
    }


}
