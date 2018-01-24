package com.cjt.admin.controller.security;

import com.cjt.admin.controller.BaseController;
import com.cjt.common.dto.UserDTO;
import com.cjt.entity.admin.security.Menu;
import com.cjt.entity.admin.security.User;
import com.cjt.service.security.IMenuService;
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

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id) {
        User user = userService.getUserByUserId(id);
        if (user == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
        return user;
    }

    @GetMapping("/{id}/menus")
    public List<Menu> getUserMenu(@PathVariable("id") Long id) {
        List<Menu> menus = menuService.listMenuByUserId(id);
        if (menus == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
        return menus;
    }

    @GetMapping(value = {"", "/"})
    public Object getUser(UserDTO userDTO) {
        if ((userDTO.getStart() == null) && (userDTO.getOffset() == null)) {
            List<User> users = userService.getUserByDTO(userDTO);
            if (users == null || users.isEmpty()) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            }
            return users;
        }
        // 分页获取
        return userService.listUserByPage(userDTO);
    }

    @PostMapping(value = {"", "/"})
    public Object saveUser(User user) {
        if (userService.saveUser(user)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            return user;
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "创建用户失败";
        }
    }

    @PutMapping("/{id}")
    public Object updateUser(@PathVariable("id") long id, @ModelAttribute("user") User user) {
        if (userService.updateUser(user)) {
            return user;
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "更新用户失败";
        }
    }

    @DeleteMapping("/{id}")
    public Object removeUserById(@PathVariable("id") long id) {
        if (userService.removeUserById(id)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return null;
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "删除用户失败";
        }
    }
}
