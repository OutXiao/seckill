/*
 * SysUserMapper.java
 * http://www.wenfan.club
 * Copyright © 2019 wenfan All Rights Reserved
 * 作者：wenfan
 * QQ：571696215
 * E-Mail：guwenfan@qq.com
 * 2019-12-28 11:18 Created
 */
package com.wenfan.seckill.mapper;

import com.wenfan.seckill.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Component
@org.apache.ibatis.annotations.Mapper
public interface SysUserMapper extends Mapper<SysUser> {

    SysUser getSysUserByUsername(String username);

    boolean changePassword(Integer userId, String newPassword);

    boolean deleteSysUserById(@Param("userId") Integer userId);

    List<SysUser> getAllUser();

    List<SysUser> getAllEnabledUser();
}