package com.wenfan.seckill.dto;

import com.wenfan.seckill.entity.SysRole;
import com.wenfan.seckill.entity.SysUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wenfan on 2019/12/28 15:17
 */
public class SysUserDto extends SysUser {
    private static final long serialVersionUID = -184009306207076712L;

    private List<SysRole> roles;

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }


    public SysUserDto(){}

    public SysUserDto(String username,String password){
        List<SysRole> list = new ArrayList<>();
        SysRole sysRole = new SysRole();
        sysRole.setId(1);
        list.add(sysRole);
        this.setOpenid("");
        this.setRoles(list);
        this.setUpdatetime(new Date());
        this.setNickname("nick"+username);
        this.setCreatetime(new Date());
        this.setPassword(password);
        this.setUsername(username);
        this.setSex(1);
        this.setPhone(username);
        this.setTelephone(username);
        this.setEmail("");
        this.setHeadimgurl("/images/defaultHead.jpg");
        this.setBirthday(new Date());
        this.setIsenabled(false);
        this.setIsexpired(false);
        this.setIslocked(false);
    }

}
