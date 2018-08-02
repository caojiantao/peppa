package com.cjt.admin.controller.system.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.caojiantao.common.util.JsonUtils;
import com.cjt.admin.controller.BaseController;
import com.cjt.entity.dto.UserDTO;
import com.cjt.entity.model.system.security.UserDO;
import com.cjt.service.system.security.IMenuService;
import com.cjt.service.system.security.IRoleService;
import com.cjt.service.system.security.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/system/security/user")
public class UserController extends BaseController {

    private final IUserService userService;

    private final IMenuService menuService;

    private final IRoleService roleService;

    @Autowired
    public UserController(IUserService userService, IMenuService menuService, IRoleService roleService) {
        this.userService = userService;
        this.menuService = menuService;
        this.roleService = roleService;
    }

    @GetMapping("/getUserById")
    public Object getUserById(int id) {
        return success(userService.getUserById(id));
    }

    @GetMapping("/getUserWithRoleIdsByUserId")
    public Object getUserWithRoleIdsByUserId(int id) {
        JSONObject object = (JSONObject) JSON.toJSON(userService.getUserById(id));
        List<Integer> roleIds = new ArrayList<>();
        roleService.getRolesByUserId(id).forEach(role -> roleIds.add(role.getId()));
        object.put("roleIds", roleIds);
        return success(object);
    }

    @GetMapping("/getUserWithMenusByUserId")
    public Object getUserWithMenusByUserId(int id) {
        JSONObject object = (JSONObject) JSON.toJSON(userService.getUserById(id));
        object.put("menus", menuService.getMenusByRoleId(id));
        return success(object);
    }

    @GetMapping("/getUserPageData")
    public Object getUserPageData(UserDTO userDTO) {
        List<UserDO> users = userService.listUsers(userDTO);
        int total = userService.countUsers(userDTO);
        return success(JsonUtils.toPageData(users, total));
    }

    @PostMapping("/saveUser")
    public Object saveUser(UserDO user, @RequestParam("roleIds") List<Integer> roleIds) {
        return userService.saveUser(user, roleIds) ? success("操作成功", user) : failure("操作失败请重试");
    }

    @PostMapping("/deleteUserById")
    public Object deleteUserById(int id) {
        return userService.deleteUserById(id) ? success("操作成功") : failure("操作失败请重试");
    }
}
