package com.wenfan.seckill.controller;


import com.wenfan.seckill.dto.SysUserDto;
import com.wenfan.seckill.entity.PageBean;
import com.wenfan.seckill.entity.SysPermission;
import com.wenfan.seckill.entity.SysRole;
import com.wenfan.seckill.entity.SysUser;
import com.wenfan.seckill.exception.SystemException;
import com.wenfan.seckill.mapper.SysPermissionMapper;
import com.wenfan.seckill.mapper.SysRoleMapper;
import com.wenfan.seckill.mapper.SysRoleUserMapper;
import com.wenfan.seckill.mapper.SysUserMapper;
import com.wenfan.seckill.rest.RestMsg;
import com.wenfan.seckill.service.SysPermissionService;
import com.wenfan.seckill.service.UserService;
import com.wenfan.seckill.utils.DateUtils;
import com.wenfan.seckill.utils.SysUserUtil;
import com.wenfan.seckill.vo.EntityVO;
import com.wenfan.seckill.vo.LoginUser;
import com.wenfan.seckill.vo.ResponseInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wenfan on 2019/12/30 19:00
 */
@RestController
@RequestMapping("/sys/admin/user")
public class SysUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysPermissionService sysPermissionService;

    @PostMapping("/allUser")
    public EntityVO getAllUsers(HttpServletRequest request) {
        String strPageIndex = request.getParameter("pageIndex");
        String strPageSize = request.getParameter("pageSize");
        int recordsTotal = 0;
        int recordsFiltered;
        int pageIndex = Integer.parseInt(strPageIndex);
        int pageSize = Integer.parseInt(strPageSize);
        PageBean<SysUser> page = userService.getSysUsersByPage(pageIndex, pageSize);
        recordsTotal = page.getTotalNum();
        recordsFiltered = recordsTotal;
        return new EntityVO(recordsTotal, recordsFiltered, page.getItems());

    }

    @DeleteMapping("deleteUser/{id}")
    public ResponseInfo deleteUser(@PathVariable("id") String id) {
        userService.deleteSysUser(Integer.parseInt(id));
        return new ResponseInfo("200", "删除成功");
    }

    @GetMapping("getSingleUser/{id}")
    public ResponseInfo<SysUserDto> getUserById(@PathVariable("id") String id) {
        SysUser user = sysUserMapper.selectByPrimaryKey(Integer.parseInt(id));
        List<SysRole> roles = sysRoleMapper.getRolesByUserId(Integer.parseInt(id));
        SysUserDto sysUserDto = new SysUserDto();
        BeanUtils.copyProperties(user, sysUserDto);
        sysUserDto.setRoles(roles);
        return new ResponseInfo("200", "ok", sysUserDto);
    }

    @PostMapping("updateUser/{id}")
    public ResponseInfo updateUser(@PathVariable("id") String id, HttpServletRequest request) {
        String username = request.getParameter("username");
        String nickname = request.getParameter("nickname");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String sex = request.getParameter("sex");
        String roleId = request.getParameter("role");
        SysUser user = sysUserMapper.selectByPrimaryKey(Integer.parseInt(id));
        user.setUsername(username);
        user.setNickname(nickname);
        user.setPhone(phone);
        user.setEmail(email);
        user.setSex(Integer.parseInt(sex));
        user.setUpdatetime(new Date());
        userService.editSysUser(user, roleId);
        return new ResponseInfo("200", "修改成功");
    }

    @PostMapping("/addUser")
    public ResponseInfo addSysUser(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String nickname = request.getParameter("nickname");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String sex = request.getParameter("sex");
        String birthday = request.getParameter("birthday");
        String roleId = request.getParameter("role");
        SysUserDto sysUser = new SysUserDto();
        sysUser.setUsername(username);
        sysUser.setPassword(password);
        sysUser.setOpenid("");
        sysUser.setHeadimgurl("/images/defaultHead.jpg");
        sysUser.setNickname(nickname);
        sysUser.setPhone(phone);
        sysUser.setEmail(email);
        sysUser.setSex(Integer.parseInt(sex));
        sysUser.setBirthday(DateUtils.stringConvertToDate(birthday));
        sysUser.setTelephone("");
        sysUser.setCreatetime(new Date());
        sysUser.setUpdatetime(new Date());
        sysUser.setIsenabled(false);
        sysUser.setIsexpired(false);
        sysUser.setIslocked(false);
        // 设置角色id
        List<SysRole> roles = new ArrayList<>();
        SysRole role = new SysRole();
        role.setId(Integer.parseInt(roleId));
        roles.add(role);
        sysUser.setRoles(roles);
        userService.addSysUser(sysUser);
        return new ResponseInfo("200", "添加成功");
    }

    @GetMapping("/search/{username}")
    public EntityVO searchUser(@PathVariable("username") String username, HttpServletRequest request) {
        String strPageIndex = request.getParameter("pageIndex");
        String strPageSize = request.getParameter("pageSize");
        int recordsTotal = 0;
        int recordsFiltered;
        int pageIndex = Integer.parseInt(strPageIndex);
        int pageSize = Integer.parseInt(strPageSize);
        PageBean<SysUser> page = userService.getSysUsersByPageAndUsername(pageIndex, pageSize, username);
        recordsTotal = page.getTotalNum();
        recordsFiltered = recordsTotal;
        return new EntityVO(recordsTotal, recordsFiltered, page.getItems());

    }

    @GetMapping("/current")
    public ResponseInfo<SysPermission> getCurrentLoginUser() {

        SysUser user = SysUserUtil.getLoginUser();
        if (user == null)
            throw new SystemException(RestMsg.Not_LOGIN);
        List<SysPermission> permissions = sysPermissionMapper.listPermissionsByUserId(user.getId());
        return new ResponseInfo<>(RestMsg.SUCCESS, permissions);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('sys:admin:update, sys:admin:delete, sys:admin:query, sys:admin:add')")
    public String getAdminPermission() {
        return "admin";
    }

    @GetMapping("/admin1")
    @PreAuthorize("hasAnyAuthority('a')")
    public String getAdminPermission1() {
        return "admin";
    }

}
