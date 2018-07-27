package com.cjt.admin.controller.security;

import com.alibaba.fastjson.JSONObject;
import com.cjt.admin.annotation.RequestEntity;
import com.cjt.admin.controller.BaseController;
import com.cjt.entity.dto.RoleDTO;
import com.cjt.entity.model.security.Role;
import com.cjt.service.security.IMenuService;
import com.cjt.service.security.IRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/system/security/roles")
public class RoleController extends BaseController {

    private final IRoleService roleService;

    private final IMenuService menuService;

    @Autowired
    public RoleController(IRoleService roleService, IMenuService menuService) {
        this.roleService = roleService;
        this.menuService = menuService;
    }

    @GetMapping("/{id}")
    public Object getRoleById(@PathVariable("id") int id) {
        JSONObject object = new JSONObject();
        object.put("role", roleService.getRoleById(id));
        object.put("menus", menuService.listMenuByRoleId(id));
        return success(object);
    }

    @GetMapping("/{id}/menus")
    public Object listMenuByRoleId(@PathVariable("id") int id) {
        return success(menuService.listMenuByRoleId(id));
    }

    @GetMapping("")
    public Object listRole(RoleDTO roleDTO) {
        return success(roleService.listRoleByPage(roleDTO));
    }

    @PostMapping("")
    public Object saveRole(@RequestEntity("role") Role role, @RequestParam("menuIds") List<Integer> menuIds) {
        if (StringUtils.isBlank(role.getName())) {
            return failure("角色名不能为空");
        }
        return roleService.saveRole(role, menuIds) ? success("操作成功", role) : failure("操作失败请重试");
    }

    @PostMapping("test")
    public Object saveRole(@RequestEntity("role") Role role) {
        return failure("角色名不能为空");
    }

    @DeleteMapping("/{id}")
    public Object removeRole(@PathVariable("id") int id) {
        return roleService.removeRole(id) ? success("操作成功") : failure("操作失败请重试");
    }
}
