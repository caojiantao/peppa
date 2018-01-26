package com.cjt.admin.controller.security;

import com.alibaba.fastjson.JSONObject;
import com.cjt.admin.controller.BaseController;
import com.cjt.entity.dto.RoleDTO;
import com.cjt.entity.model.security.Role;
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

    @Autowired
    private IRoleService roleService;

    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable("id") int id) {
        return roleService.getRoleById(id);
    }

    @GetMapping("")
    public JSONObject listRole(RoleDTO roleDTO) {
        return roleService.listRoleByPage(roleDTO);
    }

    @PostMapping("")
    public Object saveRole(Role role, @RequestParam("menuIds") List<Integer> menuIds) {
        if (roleService.saveRole(role, menuIds)) {
            return role;
        }
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return OPERATION_FAILED;
    }
}
