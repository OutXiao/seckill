package com.wenfan.seckill.controller;

import com.google.common.collect.Lists;
import com.wenfan.seckill.entity.SysPermission;
import com.wenfan.seckill.exception.SystemException;
import com.wenfan.seckill.mapper.SysPermissionMapper;
import com.wenfan.seckill.rest.RestMsg;
import com.wenfan.seckill.service.SysPermissionService;
import com.wenfan.seckill.utils.StringUtils;
import com.wenfan.seckill.vo.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.xml.ws.Response;
import java.util.List;

/**
 * Created by wenfan on 2019/12/31 17:14
 */
@RestController
@RequestMapping("/sys/admin/permissions")
public class PermissionController {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysPermissionService sysPermissionService;


    @GetMapping("/all")
    public ResponseInfo<SysPermission> getAllPermissions(){
        List<SysPermission> permissions = sysPermissionMapper.selectAll();
        List<SysPermission> list = Lists.newArrayList();
        setPermissionsList(0, permissions, list);

        return new ResponseInfo<>(RestMsg.SUCCESS, list);
    }


    private void setPermissionsList(int pId, List<SysPermission> permissionsAll, List<SysPermission> list) {
        for (SysPermission per : permissionsAll) {
            if (per.getParentId().equals(pId)) {
                list.add(per);
                if (permissionsAll.stream().filter(p -> p.getParentId().equals(per.getId())).findFirst() != null) {
                    setPermissionsList(per.getId(), permissionsAll, list);
                }
            }
        }
    }


    @GetMapping("/{roleId}")
    public ResponseInfo<SysPermission> getPermissionByRoleId(@PathVariable("roleId") Integer roleId){
        return new ResponseInfo<>(RestMsg.SUCCESS,sysPermissionMapper.listPermissionByRoleId(roleId));
    }

    @PostMapping("/update")
    public ResponseInfo updateRoleAndItsPermission(
            @RequestParam("roleId") Integer roleId
            ,@RequestParam("selectedPermissionId") String selectedPermissionId
            ,@RequestParam("name") String name
            ,@RequestParam("description") String description){
        if (StringUtils.isAllEmptys(name,description,selectedPermissionId,roleId.toString()))
            throw new SystemException(RestMsg.ERROR_PARAMETER);
        sysPermissionService.updateRoleAndPermission(roleId,name,description,selectedPermissionId);
        return new ResponseInfo(RestMsg.SUCCESS);
    }


    @PostMapping("/add")
    public ResponseInfo addRoleAndItsPermission(
            @RequestParam("selectedPermissionId") String selectedPermissionId
            ,@RequestParam("name") String name
            ,@RequestParam("description") String description){
        if (StringUtils.isAllEmptys(name,description,selectedPermissionId))
            throw new SystemException(RestMsg.ERROR_PARAMETER);
        sysPermissionService.addRoleAndPermission(name,description,selectedPermissionId);
        return new ResponseInfo(RestMsg.SUCCESS);
    }


    @GetMapping("/allParent")
    public ResponseInfo<SysPermission> getAllMenuParents(){
        Example example = new Example(SysPermission.class);
        example.createCriteria().andEqualTo("parentId",0);
        List<SysPermission> sysPermissions = sysPermissionMapper.selectByExample(example);
       return ResponseInfo.success(sysPermissions);
    }

    @PostMapping("/edit/{permissionId}")
    public ResponseInfo editPermission(
            @PathVariable("permissionId") String permissionId
            ,@RequestParam("name") String name
            ,@RequestParam("icon") String icon
            ,@RequestParam("url") String url
            ,@RequestParam("sort") Integer sort
            ,@RequestParam("parentId") Integer parentId
    ){
        SysPermission sysPermission = new SysPermission();
        sysPermission.setId(Integer.parseInt(permissionId));
        sysPermission.setHref(url);
        sysPermission.setName(name);
        sysPermission.setSort(sort);
        sysPermission.setParentId(parentId);
        sysPermissionService.editPermission(sysPermission);
        return ResponseInfo.success();
    }

    @GetMapping("/getPermission/{permissionId}")
    public ResponseInfo getPermissionById(@PathVariable("permissionId") String permissionId){
        SysPermission sysPermission = sysPermissionMapper.getPermissionById(Integer.valueOf(permissionId));
        return ResponseInfo.success(sysPermission);
    }


    @PostMapping("/addPermission")
    public ResponseInfo addPermission(
            @RequestParam("name") String name
            ,@RequestParam("icon") String icon
            ,@RequestParam("url") String url
            ,@RequestParam("sort") Integer sort
            ,@RequestParam("parentId") Integer parentId){
        SysPermission sysPermission = new SysPermission();
        sysPermission.setParentId(parentId);
        sysPermission.setName(name);
        sysPermission.setHref(url);
        sysPermission.setSort(sort);
        // 菜单类型为0 -- 表示为菜单或者连接  不是权限
        sysPermission.setType(false);
        sysPermission.setPermission("");
        sysPermission.setIcon(icon);
        int i = sysPermissionService.addPermission(sysPermission);
        if (i < 0)
            return ResponseInfo.fail();
        return ResponseInfo.success();
    }


    @GetMapping("/del/{permissionId}")
    public ResponseInfo deletePermission(@PathVariable("permissionId") String permissionId){
        sysPermissionService.delPermission(Integer.parseInt(permissionId));

        return ResponseInfo.success();
    }


}
