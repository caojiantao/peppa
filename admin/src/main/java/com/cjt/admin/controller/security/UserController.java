package com.cjt.admin.controller.security;

import com.cjt.admin.controller.BaseController;
import com.cjt.entity.dto.UserDTO;
import com.cjt.entity.model.security.Menu;
import com.cjt.entity.model.security.User;
import com.cjt.service.security.IMenuService;
import com.cjt.service.security.IRoleService;
import com.cjt.service.security.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IRoleService roleService;

    @GetMapping("/{id}")
    public Object getUser(@PathVariable("id") Long id) {
        User user = userService.getUserByUserId(id);
        if (user == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return NOT_FOUND;
        }
        return user;
    }

    @GetMapping("/{id}/menus")
    public Object getUserMenu(@PathVariable("id") Long id) {
        List<Menu> menus = menuService.listMenuByUserId(id);
        if (menus == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return NOT_FOUND;
        }
        return menus;
    }

    @GetMapping(value = {"", "/"})
    public Object getUser(UserDTO userDTO) {
        if ((userDTO.getStart() == null) && (userDTO.getOffset() == null)) {
            List<User> users = userService.getUserByDTO(userDTO);
            if (users == null || users.isEmpty()) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return NOT_FOUND;
            }
            return users;
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
        if (userService.saveUser(user, roleIds)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            return user;
        }
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return OPERATION_FAILED;

    }

    @PutMapping(value = {"", "/"})
    public Object updateUser(User user, @RequestParam("roleIds") List<Integer> roleIds) {
        if (userService.updateUser(user, roleIds)) {
            return user;
        }
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return OPERATION_FAILED;

    }

    @DeleteMapping("/{id}")
    public Object removeUserById(@PathVariable("id") long id) {
        if (userService.removeUserById(id)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return null;
        }
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return OPERATION_FAILED;
    }
}
