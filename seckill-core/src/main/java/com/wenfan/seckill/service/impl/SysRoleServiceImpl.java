package com.wenfan.seckill.service.impl;

import com.wenfan.seckill.dto.SysRoleAndItsPermission;
import com.wenfan.seckill.entity.PageBean;
import com.wenfan.seckill.entity.SysPermission;
import com.wenfan.seckill.entity.SysRole;
import com.wenfan.seckill.exception.SystemException;
import com.wenfan.seckill.mapper.SysPermissionMapper;
import com.wenfan.seckill.mapper.SysRoleMapper;
import com.wenfan.seckill.mapper.SysRoleUserMapper;
import com.wenfan.seckill.service.SysRoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wenfan on 2020/1/19 13:15
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public PageBean<SysRole> getSysRolesByPage(int pageIndex, int pageSize) {
        Page page = PageHelper.startPage(pageIndex, pageSize);
        List<SysRole> allSysRoles = roleMapper.selectAll();
        int count = allSysRoles.size();
        PageBean<SysRole> pageBean = new PageBean<>(pageIndex, pageSize, count);
        pageBean.setItems(allSysRoles);
        pageBean.setTotalNum((int) page.getTotal());
        return pageBean;
    }

    @Override
    public SysRoleAndItsPermission getRoleAndPerByRoleId(Integer roleId) {

        SysRole sysRole = roleMapper.selectByPrimaryKey(roleId);
        if (sysRole == null)
            throw new SystemException("系统无该角色");
        List<SysPermission> permissions = sysPermissionMapper.listPermissionByRoleId(roleId);
        return new SysRoleAndItsPermission(sysRole, permissions);
    }


}
