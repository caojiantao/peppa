package com.cjt.admin.controller.security;

import com.cjt.admin.controller.BaseController;
import com.cjt.entity.admin.security.Menu;
import com.cjt.service.security.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/menus")
public class MenuController extends BaseController {

    @Autowired
    private IMenuService menuService;

    @GetMapping("/{id}")
    public Object getMenuBy(@PathVariable("id") int id) {
        Menu menu = menuService.getMenuById(id);
        if (menu == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "该菜单不存在";
        }
        return menu;
    }

    @PostMapping("")
    public Object insertMenu(Menu menu) {
        if (menuService.saveMenu(menu)) {
            return menu;
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "新增菜单失败";
        }
    }

    @PutMapping("")
    public Object updateMenu(Menu menu) {
        if (menuService.updateMenu(menu)) {
            return menu;
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "更新菜单失败";
        }
    }

    @DeleteMapping("/{id}")
    public Object removeMenuById(@PathVariable("id") int id) {
        if (menuService.removeMenuById(id)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return null;
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "删除菜单失败";
        }
    }
}
