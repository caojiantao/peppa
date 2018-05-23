package com.cjt.admin.controller.security;

import com.cjt.admin.controller.BaseController;
import com.cjt.entity.model.security.Menu;
import com.cjt.service.security.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/menus")
public class MenuController extends BaseController {

    private final IMenuService menuService;

    @Autowired
    public MenuController(IMenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/{id}")
    public Object getMenuById(@PathVariable("id") int id, HttpServletResponse response) {
        Menu menu = menuService.getMenuById(id);
        if (menu == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return NOT_FOUND;
        }
        return menu;
    }

    @GetMapping(value = {"", "/"})
    public Object listMenu(HttpServletResponse response) {
        List<Menu> menus = menuService.listMenu();
        if (menus == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return NOT_FOUND;
        }
        return menus;
    }

    @PostMapping("")
    public Object insertMenu(Menu menu, HttpServletResponse response) {
        if (menuService.saveMenu(menu)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            return menu;
        }
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return OPERATION_FAILED;
    }

    @PutMapping("")
    public Object updateMenu(Menu menu, HttpServletResponse response) {
        if (menuService.updateMenu(menu)) {
            return menu;
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return OPERATION_FAILED;
        }
    }

    @DeleteMapping("/{id}")
    public Object removeMenuById(@PathVariable("id") int id, HttpServletResponse response) {
        if (menuService.removeMenuById(id)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return null;
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return OPERATION_FAILED;
        }
    }
}
