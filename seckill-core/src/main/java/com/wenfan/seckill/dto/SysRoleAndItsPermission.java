package com.wenfan.seckill.dto;

import com.wenfan.seckill.entity.SysPermission;
import com.wenfan.seckill.entity.SysRole;

import java.util.List;

/**
 * Created by wenfan on 2020/1/19 21:16
 */
public class SysRoleAndItsPermission {

    private SysRole sysRole;

    private List<SysPermission> permissions;

    public SysRoleAndItsPermission(SysRole role, List<SysPermission> permissions) {

        this.sysRole = role;
        this.permissions = permissions;
    }

    public SysRole getSysRole() {
        return sysRole;
    }

    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }

    public List<SysPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SysPermission> permissions) {
        this.permissions = permissions;
    }
}
