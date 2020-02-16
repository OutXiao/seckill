package com.wenfan.seckill.service;

import com.wenfan.seckill.dto.SysUserDto;
import com.wenfan.seckill.entity.PageBean;
import com.wenfan.seckill.entity.SysUser;
import com.wenfan.seckill.exception.SystemException;

/**
 * Created by wenfan on 2019/12/28 15:11
 */
public interface UserService {

    PageBean<SysUser> getSysUsersByPage(int pageIndex, int pageSize);

    int editSysUser(SysUser sysUser, String roleId);

    boolean deleteSysUser(Integer userId);

    PageBean<SysUser> getSysUserByNickNameAndPage(int pageIndex, int pageSize, String nickname);

    PageBean<SysUser> getSysUserByStatusAndPage(int pageIndex, int pageSize, int status);

    SysUser addSysUser(SysUserDto userDto) throws SystemException;

    SysUser getSysUser(String username);

    PageBean<SysUser> getSysUsersByPageAndUsername(int pageIndex, int pageSize, String username);

    boolean changePassword(String ussername, String oldPassword, String newPassword);
}
