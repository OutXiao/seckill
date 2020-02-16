package com.wenfan.seckill.service.impl;

import com.wenfan.seckill.dto.SysUserDto;
import com.wenfan.seckill.entity.PageBean;
import com.wenfan.seckill.entity.SysRole;
import com.wenfan.seckill.entity.SysRoleUser;
import com.wenfan.seckill.entity.SysUser;
import com.wenfan.seckill.exception.SystemException;
import com.wenfan.seckill.mapper.SysRoleUserMapper;
import com.wenfan.seckill.mapper.SysUserMapper;
import com.wenfan.seckill.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenfan on 2019/12/28 15:20
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleUserMapper roleUserMapper;


    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public PageBean<SysUser> getSysUsersByPage(int pageIndex, int pageSize) {
        Page page = PageHelper.startPage(pageIndex, pageSize);
        List<SysUser> allSysUsers = userMapper.getAllEnabledUser();
        int count = allSysUsers.size();
        PageBean<SysUser> pageBean = new PageBean<>(pageIndex, pageSize, count);
        pageBean.setItems(allSysUsers);
        pageBean.setTotalNum((int) page.getTotal());
        return pageBean;
    }

    @Override
    @Transactional
    public int editSysUser(SysUser sysUser, String roleId) {

        List<SysRoleUser> roleUsers = roleUserMapper.getRoleUserMappingByUserId(sysUser.getId());
        if (roleUsers.size() > 0) {
            roleUsers.get(0).setRoleid(Integer.parseInt(roleId));
            roleUserMapper.updateACertainRoleByUserId(roleUsers.get(0).getUserid(), roleUsers.get(0).getRoleid());
        } else {
            // 该用户无权限 ，添加对应的权限
            roleUserMapper.insertARoleOnACertainUserId(sysUser.getId(), Integer.parseInt(roleId));
            log.info("给 " + sysUser.getUsername() + " 添加了{} :", roleId);
        }
        log.info("更新：{}", sysUser.getUsername());
        return userMapper.updateByPrimaryKey(sysUser);
    }

    @Override
    public boolean deleteSysUser(Integer userId) {
        return userMapper.deleteSysUserById(userId);
    }

    @Override
    public PageBean<SysUser> getSysUserByNickNameAndPage(int pageIndex, int pageSize, String nickname) {
        return null;
    }

    @Override
    public PageBean<SysUser> getSysUserByStatusAndPage(int pageIndex, int pageSize, int status) {
        return null;
    }

    @Override
    @Transactional
    public SysUser addSysUser(SysUserDto userDto) throws SystemException {

        SysUser user = userMapper.getSysUserByUsername(userDto.getUsername());
        if (user != null) {
            throw new SystemException("200", "该用户名已被注册！");
        } else {
            if (!StringUtils.isEmpty(userDto.getPassword())) {
                userDto.setPassword(encoder.encode(userDto.getPassword()));
            }
            userDto.setStatus(SysUser.Status.VALID);
            userMapper.insert(userDto);
            if (!CollectionUtils.isEmpty(userDto.getRoles())) {
                roleUserMapper.insertARoleOnACertainUserId(userDto.getId(), userDto.getRoles().get(0).getId());
            }
            log.info("新增 {}", userDto.getUsername());
        }
        return userDto;
    }


    // 添加用户角色
    private boolean saveUserRoles(Integer userId, List<SysRole> rolesId) {
        if (userId != null) {
            roleUserMapper.deleteASeriesOfRoleOnAnCertainRoleId(userId);
            if (!CollectionUtils.isEmpty(rolesId)) {
                return roleUserMapper.insertASeriesOfRoleOnAnCertainRoleId(userId, rolesId);
            }
        }
        return false;
    }

    @Override
    public SysUser getSysUser(String username) {
        return userMapper.getSysUserByUsername(username);
    }

    @Override
    public PageBean<SysUser> getSysUsersByPageAndUsername(int pageIndex, int pageSize, String username) {
        Page page = PageHelper.startPage(pageIndex, pageSize);
        List<SysUser> users = new ArrayList<>();
        SysUser user = userMapper.getSysUserByUsername(username);
        /*if(user == null){
            throw new SystemException("无该用户");
        }*/
        users.add(user);
        int count = users.size();
        PageBean<SysUser> pageBean = new PageBean<>(pageIndex, pageSize, count);
        pageBean.setItems(users);
        pageBean.setTotalNum((int) page.getTotal());
        return pageBean;
    }


    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        SysUser user = userMapper.getSysUserByUsername(username);
        if (user != null) {
            if (encoder.matches(oldPassword, user.getPassword())) {
                boolean result = userMapper.changePassword(user.getId(), encoder.encode(newPassword));
                log.info("{} 修改密码成功", user.getId());
                return result;
            } else {
                throw new IllegalArgumentException("旧密码错误");

            }
        } else {
            throw new IllegalArgumentException("用户不存在");
        }
    }


}
