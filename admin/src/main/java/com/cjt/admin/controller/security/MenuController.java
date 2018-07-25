package com.cjt.admin.controller.security;

import com.cjt.admin.controller.BaseController;
import com.cjt.entity.model.security.Menu;
import com.cjt.service.security.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Object getMenuById(@PathVariable("id") int id) {
        return menuService.getMenuById(id);
    }

    @GetMapping("")
    public Object listMenu() {
        return menuService.listMenu();
    }

    @PostMapping("")
    public Object insertMenu(Menu menu) {
        return menuService.saveMenu(menu) ? success("操作成功", menu) : failure("操作失败请重试");
    }

    @PutMapping("")
    public Object updateMenu(Menu menu) {
        return menuService.updateMenu(menu) ? success("操作成功", menu) : failure("操作失败请重试");
    }

    @DeleteMapping("/{id}")
    public Object removeMenuById(@PathVariable("id") int id) {
        return menuService.removeMenuById(id) ? success("操作成功") : failure("操作失败请重试");
    }
}
