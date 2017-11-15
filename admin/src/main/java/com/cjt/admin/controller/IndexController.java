package com.cjt.admin.controller;

import com.cjt.entity.admin.Menu;
import com.cjt.service.IMenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("")
public class IndexController extends BaseController {

  @Resource
  private IMenuService menuService;

  @RequestMapping("/")
  public String index(Model model){
    List<Menu> menus = menuService.listMenus();
    model.addAttribute("menus", menus);
    return "index";
  }
}
