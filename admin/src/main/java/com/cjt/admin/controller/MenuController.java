package com.cjt.admin.controller;

import com.cjt.entity.admin.Menu;
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
public class MenuController {

    @Resource
    private IMenuService menuService;

    @GetMapping(value = {"", "/"})
    public List<Menu> listMenus(){
        return menuService.listMenus();
    }
}
