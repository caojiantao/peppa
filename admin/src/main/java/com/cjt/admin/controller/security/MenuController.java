package com.cjt.admin.controller.security;

import com.cjt.admin.controller.BaseController;
import com.cjt.entity.model.security.Menu;
import com.cjt.service.security.IMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/system/security/menus")
public class MenuController extends BaseController {

    private final IMenuService menuService;

    @Autowired
    public MenuController(IMenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/{id}")
    public Object getById(@PathVariable("id") int id) {
        return success(menuService.getMenuById(id));
    }

    @GetMapping("")
    public Object list() {
        return success(menuService.listMenu());
    }

    @PostMapping("")
    public Object save(Menu menu) {
        // 字段校验
        if (StringUtils.isBlank(menu.getName())) {
            return failure("菜单名称不能为空");
        } else if (menu.getName().length() > 20) {
            return failure("菜单名称不能超过20个字符");
        } else if (menu.getOrder() == null) {
            return failure("菜单排序不能为空");
        }
        return menuService.saveMenu(menu) ? success("操作成功", menu) : failure("操作失败请重试");
    }

    @PostMapping("/delete")
    public Object deleteById(int id) {
        return menuService.removeMenuById(id) ? success("操作成功") : failure("操作失败请重试");
    }
}
