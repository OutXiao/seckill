package com.wenfan.seckill.controller;

import com.wenfan.seckill.dto.SysRoleAndItsPermission;
import com.wenfan.seckill.entity.PageBean;
import com.wenfan.seckill.entity.SysRole;
import com.wenfan.seckill.mapper.SysRoleMapper;
import com.wenfan.seckill.service.SysRoleService;
import com.wenfan.seckill.vo.EntityVO;
import com.wenfan.seckill.vo.ResponseInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by wenfan on 2020/1/14 13:31
 * 系统角色
 */
@RestController
@RequestMapping("/sys/admin/role")
public class SysRoleController {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/allRole")
    public ResponseInfo<SysRole> getAllSysRole() {
        List<SysRole> roles = sysRoleMapper.selectAll();
        return new ResponseInfo("200", "ok", roles);
    }

    @GetMapping("/allRoleByPage")
    public EntityVO<SysRole> getAllSysRoleByPage(HttpServletRequest request) {
        String strPageIndex = request.getParameter("pageIndex");
        String strPageSize = request.getParameter("pageSize");
        int recordsTotal = 0;
        int recordsFiltered;
        int pageIndex = Integer.parseInt(strPageIndex);
        int pageSize = Integer.parseInt(strPageSize);
        PageBean<SysRole> page = sysRoleService.getSysRolesByPage(pageIndex, pageSize);
        recordsTotal = page.getTotalNum();
        recordsFiltered = recordsTotal;
        return new EntityVO(recordsTotal, recordsFiltered, page.getItems());
    }


    @GetMapping("/getSingleRole/{id}")
    public ResponseInfo getSysRoleAndItsPermissionsById(@PathVariable("id") String id) {
        if (StringUtils.isEmpty(id))
            throw new IllegalArgumentException();
        SysRoleAndItsPermission rs = sysRoleService.getRoleAndPerByRoleId(Integer.parseInt(id));
        return ResponseInfo.success(rs);
    }

}
