package com.cjt.admin.controller.security;

import com.alibaba.fastjson.JSONObject;
import com.cjt.admin.controller.BaseController;
import com.cjt.entity.dto.RoleDTO;
import com.cjt.entity.model.security.Menu;
import com.cjt.entity.model.security.Role;
import com.cjt.service.security.IMenuService;
import com.cjt.service.security.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
        List<Menu> menus = menuService.listMenuByRoleId(id);
        if (menus == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return menus;
    }

    @GetMapping("")
    public JSONObject listRole(RoleDTO roleDTO) {
        return roleService.listRoleByPage(roleDTO);
    }

    @PostMapping("")
    public Object saveRole(Role role, @RequestParam("menuIds") List<Integer> menuIds) {
        if (roleService.saveRole(role, menuIds)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            return role;
        }
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return OPERATION_FAILED;
    }

    @PutMapping("")
    public Object updateRole(Role role, @RequestParam("menuIds") List<Integer> menuIds) {
        if (roleService.updateRole(role, menuIds)) {
            return role;
        }
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return OPERATION_FAILED;
    }

    @DeleteMapping("/{id}")
    public Object removeRole(@PathVariable("id") int id) {
        if (roleService.removeRole(id)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return null;
        }
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return OPERATION_FAILED;
    }
}
