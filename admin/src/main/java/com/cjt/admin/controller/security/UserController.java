package com.cjt.admin.controller.security;

import com.cjt.admin.controller.BaseController;
import com.cjt.entity.dto.UserDTO;
import com.cjt.entity.model.security.User;
import com.cjt.service.security.IMenuService;
import com.cjt.service.security.IRoleService;
import com.cjt.service.security.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/users")
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

    @GetMapping("/{id}")
    public Object getUser(@PathVariable("id") Long id) {
        return userService.getUserByUserId(id);
    }

    @GetMapping("/{id}/menus")
    public Object getUserMenu(@PathVariable("id") Long id) {
        return success("操作成功", menuService.listMenuByUserId(id));
    }

    @GetMapping("")
    public Object getUser(UserDTO userDTO) {
        if ((userDTO.getStart() == null) && (userDTO.getOffset() == null)) {
            return userService.getUserByDTO(userDTO);
        }
        // 分页获取
        return userService.listUserByPage(userDTO);
    }

    @GetMapping("/{id}/roles")
    public Object listRoleByUserId(@PathVariable("id") long id) {
        return roleService.listRoleByUserId(id);
    }

    @PostMapping("")
    public Object saveUser(User user, @RequestParam("roleIds") List<Integer> roleIds) {
        return userService.saveUser(user, roleIds) ? success("操作成功", user) : failure("操作失败请重试");

    }

    @PutMapping("")
    public Object updateUser(User user, @RequestParam("roleIds") List<Integer> roleIds) {
        return userService.updateUser(user, roleIds) ? success("操作成功", user) : failure("操作失败请重试");
    }

    @DeleteMapping("/{id}")
    public Object removeUserById(@PathVariable("id") long id) {
        return userService.removeUserById(id) ? success("操作成功") : failure("操作失败请重试");
    }
}
