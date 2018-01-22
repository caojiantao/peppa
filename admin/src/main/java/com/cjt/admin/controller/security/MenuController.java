package com.cjt.admin.controller.security;

import com.cjt.admin.controller.BaseController;
import com.cjt.entity.admin.security.Menu;
import com.cjt.entity.admin.security.User;
import com.cjt.service.IMenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/menus")
public class MenuController extends BaseController {

    @Resource
    private IMenuService menuService;

    @GetMapping(value = {"", "/"})
    public List<Menu> listMenus() {
        User user = (User) request.getAttribute("user");
        return menuService.listMenuByRoles(user.getRoles());
    }
}
