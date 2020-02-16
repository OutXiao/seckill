package com.wenfan.seckill.service;

import com.wenfan.seckill.dto.SysRoleAndItsPermission;
import com.wenfan.seckill.entity.PageBean;
import com.wenfan.seckill.entity.SysRole;

/**
 * Created by wenfan on 2020/1/19 13:14
 */
public interface SysRoleService {

    PageBean<SysRole> getSysRolesByPage(int pageIndex, int pageSize);

    SysRoleAndItsPermission getRoleAndPerByRoleId(Integer roleId);

}
