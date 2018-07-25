package com.cjt.admin.controller.security;

import com.alibaba.fastjson.JSONObject;
import com.cjt.admin.controller.BaseController;
import com.cjt.entity.dto.RoleDTO;
import com.cjt.entity.model.security.Role;
import com.cjt.service.security.IMenuService;
import com.cjt.service.security.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/roles")
public class RoleController extends BaseController {

    private final IRoleService roleService;

    private final IMenuService menuService;

    @Autowired
    public RoleController(IRoleService roleService, IMenuService menuService) {
        this.roleService = roleService;
        this.menuService = menuService;
    }

    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable("id") int id) {
        return roleService.getRoleById(id);
    }

    @GetMapping("/{id}/menus")
    public Object listMenuByRoleId(@PathVariable("id") int id) {
        return menuService.listMenuByRoleId(id);
    }

    @GetMapping("")
    public JSONObject listRole(RoleDTO roleDTO) {
        return roleService.listRoleByPage(roleDTO);
    }

    @PostMapping("")
    public Object saveRole(Role role, @RequestParam("menuIds") List<Integer> menuIds) {
        return roleService.saveRole(role, menuIds) ? success("操作成功", role) : failure("操作失败请重试");
    }

    @PutMapping("")
    public Object updateRole(Role role, @RequestParam("menuIds") List<Integer> menuIds) {
        return roleService.updateRole(role, menuIds) ? success("操作成功", role) : failure("操作失败请重试");
    }

    @DeleteMapping("/{id}")
    public Object removeRole(@PathVariable("id") int id) {
        return roleService.removeRole(id) ? success("操作成功") : failure("操作失败请重试");
    }
}
