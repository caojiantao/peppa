package com.cjt.admin.controller.security;

import com.cjt.admin.controller.BaseController;
import com.cjt.entity.admin.security.Menu;
import com.cjt.entity.admin.security.User;
import com.cjt.service.IMenuService;
import com.cjt.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/menus")
public class MenuController extends BaseController {

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IUserService userService;

    @GetMapping("")
    public List<Menu> listMenu(String username) {
        if (StringUtils.isBlank(username)) {
            return menuService.listAllMenu();
        }
        User user = userService.getUserByUsername(username);
        return menuService.listMenuByUser(user);
    }

    @PostMapping("")
    public Object insertMenu(Menu menu){
        if (menuService.saveMenu(menu)){
            return menu;
        } else {
            internalError();
            return "新增菜单失败";
        }
    }
}
