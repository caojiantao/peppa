package com.cjt.admin.controller.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.caojiantao.common.util.JsonUtils;
import com.cjt.admin.controller.BaseController;
import com.cjt.entity.dto.RoleDTO;
import com.cjt.entity.model.security.Role;
import com.cjt.service.security.IMenuService;
import com.cjt.service.security.IRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
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
    public Object getRoleById(int id) {
        JSONObject object = (JSONObject) JSON.toJSON(roleService.getRoleById(id));
        object.put("menus", menuService.getMenusByRoleId(id));
        return success(object);
    }

    @GetMapping("/getRolePageData")
    public Object getRolePageData(RoleDTO roleDTO) {
        List<Role> roles = roleService.getRoles(roleDTO);
        int total = roleService.getRolesTotal(roleDTO);
        return success(JsonUtils.toPageData(roles, total));
    }

    @GetMapping("/getRoles")
    public Object getRoles() {
        return success(roleService.getRoles(null));
    }

    @PostMapping("/saveRole")
    public Object saveRole(Role role, @RequestParam("menuIds") List<Integer> menuIds) {
        if (StringUtils.isBlank(role.getName())) {
            return failure("角色名不能为空");
        }
        return roleService.saveRole(role, menuIds) ? success("操作成功", role) : failure("操作失败请重试");
    }

    @PostMapping("/deleteRoleById")
    public Object deleteRoleById(int id) {
        return roleService.deleteRoleById(id) ? success("操作成功") : failure("操作失败请重试");
    }
}
