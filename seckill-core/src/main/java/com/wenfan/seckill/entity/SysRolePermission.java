/*
 * SysRolePermission.java
 * http://www.wenfan.club
 * Copyright © 2019 wenfan All Rights Reserved
 * 作者：wenfan
 * QQ：571696215
 * E-Mail：guwenfan@qq.com
 * 2019-12-28 11:18 Created
 */
package com.wenfan.seckill.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sys_role_permission")
public class SysRolePermission implements Serializable {
    @Id
    @Column(name = "roleId")
    private Integer roleid;

    @Id
    @Column(name = "permissionId")
    private Integer permissionid;

    private static final long serialVersionUID = 1L;

    /**
     * @return roleId
     */
    public Integer getRoleid() {
        return roleid;
    }

    /**
     * @param roleid
     */
    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    /**
     * @return permissionId
     */
    public Integer getPermissionid() {
        return permissionid;
    }

    /**
     * @param permissionid
     */
    public void setPermissionid(Integer permissionid) {
        this.permissionid = permissionid;
    }

}