package com.cjt.admin.controller.security;

import com.cjt.admin.controller.BaseController;
import com.cjt.entity.admin.security.Menu;
import com.cjt.entity.admin.security.User;
import com.cjt.service.IMenuService;
import com.cjt.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping()
public class MenuController extends BaseController {

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IUserService userService;

    @GetMapping("/menus")
    public List<Menu> listAllMenu() {
        return menuService.listAllMenu();
    }

    @GetMapping("/users/{userId}/menus")
    public List<Menu> listMenu(@PathVariable("userId") long userId) {
        User user = userService.getUserByUserId(userId);
        return menuService.listMenuByUser(user);
    }
}
