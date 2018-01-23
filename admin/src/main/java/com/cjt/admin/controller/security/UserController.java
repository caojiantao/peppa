package com.cjt.admin.controller.security;

import com.alibaba.fastjson.JSONObject;
import com.cjt.admin.controller.BaseController;
import com.cjt.common.dto.UserDTO;
import com.cjt.entity.admin.security.User;
import com.cjt.service.IMenuService;
import com.cjt.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id) {
        UserDTO.Builder builder = new UserDTO.Builder();
        User user = userService.getUserByDto(builder.id(id).build());
        if (user == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
        return user;
    }

    @GetMapping("")
    public JSONObject listUserByPage(@ModelAttribute("userDTO") UserDTO userDTO) {
        return userService.listUserByPage(userDTO);
    }

    @PostMapping("")
    public Object saveUser(@ModelAttribute("user") User user) {
        if (userService.saveUser(user)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            return user;
        } else {
            internalError();
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
