package com.wenfan.seckill.service.impl;

import com.wenfan.seckill.entity.SysPermission;
import com.wenfan.seckill.entity.SysRole;
import com.wenfan.seckill.entity.SysRolePermission;
import com.wenfan.seckill.entity.SysUser;
import com.wenfan.seckill.exception.SystemException;
import com.wenfan.seckill.mapper.SysPermissionMapper;
import com.wenfan.seckill.mapper.SysRoleMapper;
import com.wenfan.seckill.mapper.SysRolePermissionMapper;
import com.wenfan.seckill.rest.RestMsg;
import com.wenfan.seckill.service.SysPermissionService;
import com.wenfan.seckill.service.SysRoleService;
import com.wenfan.seckill.utils.SysUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by wenfan on 2020/2/11 14:10
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {


    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    @Transactional
    public int updateRoleAndPermission(Integer roleId, String name, String desc, String permissionIds) {

        SysRole sysRole = new SysRole();
        sysRole.setId(roleId);
        sysRole.setDescription(desc);
        sysRole.setName(name);
        sysRole.setUpdatetime(new Date());
        sysRoleMapper.updateByPrimaryKeySelective(sysRole);

        String permissionIdsStr [] = permissionIds.split(",");
        Integer permissions[] = new Integer[permissionIdsStr.length];
        for (int i = 0; i<permissionIdsStr.length ;i++ )
            permissions[i] = Integer.parseInt(permissionIdsStr[i]);
        sysRolePermissionMapper.deletePermissionIdByRoleId(roleId);
        return sysRolePermissionMapper.updatePermissionByRoleId(roleId,Arrays.asList(permissions));
    }

    @Override
    public int addRoleAndPermission(String name, String desc, String permissionIds) {
        SysRole sysRole = new SysRole();
        sysRole.setDescription(desc);
        sysRole.setName(name);
        sysRole.setUpdatetime(new Date());
        sysRole.setCreatetime(new Date());
        sysRoleMapper.insert(sysRole);

        String permissionIdsStr [] = permissionIds.split(",");
        Integer permissions[] = new Integer[permissionIdsStr.length];
        for (int i = 0; i<permissionIdsStr.length ;i++ )
            permissions[i] = Integer.parseInt(permissionIdsStr[i]);
        return sysRolePermissionMapper.updatePermissionByRoleId(sysRole.getId(),Arrays.asList(permissions));
    }

    @Override
    public int editPermission(SysPermission sysPermission) {
        int i = sysPermissionMapper.updateByPrimaryKeySelective(sysPermission);
        if (i < 0)
            throw new SystemException(RestMsg.INNER_ERROR);
        return i;
    }

    @Override
    @Transactional
    public int addPermission(SysPermission sysPermission) {
        SysUser currentUser = SysUserUtil.getLoginUser();
        SysRole sysRole = sysRoleMapper.getRolesByUserId(currentUser.getId()).get(0);
        int i = sysPermissionMapper.insert(sysPermission);
        SysRolePermission sysRolePermission = new SysRolePermission();
        sysRolePermission.setRoleid(sysRole.getId());
        sysRolePermission.setPermissionid(sysPermission.getId());
        int j = sysRolePermissionMapper.insert(sysRolePermission);


        return i&j;
    }

    @Override
    @Transactional
    public int delPermission(Integer permissionId) {
        // 删除 与角色对应的关系
        int i = sysRolePermissionMapper.deletePermissionIdByRoleId(permissionId);
        // 删除实体权限
        int j = sysPermissionMapper.delPermissionById(permissionId);
        return i&j;
    }
}
