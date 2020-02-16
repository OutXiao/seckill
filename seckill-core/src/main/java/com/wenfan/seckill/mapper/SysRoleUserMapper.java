/*
 * SysRoleUserMapper.java
 * http://www.wenfan.club
 * Copyright © 2019 wenfan All Rights Reserved
 * 作者：wenfan
 * QQ：571696215
 * E-Mail：guwenfan@qq.com
 * 2019-12-28 11:18 Created
 */
package com.wenfan.seckill.mapper;


import com.wenfan.seckill.entity.SysRole;
import com.wenfan.seckill.entity.SysRoleUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Component
@org.apache.ibatis.annotations.Mapper
public interface SysRoleUserMapper extends Mapper<SysRoleUser> {

    // 给某一用户添加某些角色
    boolean insertASeriesOfRoleOnAnCertainRoleId(Integer userId, List<SysRole> rolesId);

    // 给某一用户删除某些角色
    boolean deleteASeriesOfRoleOnAnCertainRoleId(Integer userId);

    List<SysRoleUser> getRoleUserMappingByUserId(Integer userId);

    boolean insertARoleOnACertainUserId(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    boolean updateACertainRoleByUserId(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

}