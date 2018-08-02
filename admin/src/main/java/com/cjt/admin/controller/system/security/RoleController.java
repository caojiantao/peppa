package com.cjt.admin.controller.system.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.caojiantao.common.util.JsonUtils;
import com.cjt.admin.controller.BaseController;
import com.cjt.entity.dto.ResultDTO;
import com.cjt.entity.dto.RoleDTO;
import com.cjt.entity.model.system.security.RoleDO;
import com.cjt.service.system.security.IMenuService;
import com.cjt.service.system.security.IRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/system/security/role")
public class RoleController extends BaseController {

    private final IRoleService roleService;

    private final IMenuService menuService;

    @Autowired
    public RoleController(IRoleService roleService, IMenuService menuService) {
        this.roleService = roleService;
        this.menuService = menuService;
    }

    @GetMapping("/getRoleWithMenusById")
    public ResultDTO getRoleById(int id) {
        JSONObject object = (JSONObject) JSON.toJSON(roleService.getRoleById(id));
        object.put("menus", menuService.getMenusByRoleId(id));
        return success(object);
    }

    @GetMapping("/getRolePageData")
    public ResultDTO getRolePageData(RoleDTO roleDTO) {
        List<RoleDO> roles = roleService.listRoles(roleDTO);
        int total = roleService.countRoles(roleDTO);
        return success(JsonUtils.toPageData(roles, total));
    }

    @GetMapping("/getRoles")
    public ResultDTO getRoles() {
        return success(roleService.listRoles(null));
    }

    @PostMapping("/saveRole")
    public ResultDTO saveRole(RoleDO role, @RequestParam("menuIds") List<Integer> menuIds) {
        if (StringUtils.isBlank(role.getName())) {
            return failure("角色名不能为空");
        }
        return roleService.saveRole(role, menuIds) ? success("操作成功", role) : failure("操作失败请重试");
    }

    @PostMapping("/deleteRoleById")
    public ResultDTO deleteRoleById(int id) {
        return roleService.deleteRoleById(id) ? success("操作成功") : failure("操作失败请重试");
    }
}
