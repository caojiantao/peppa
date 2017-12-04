package com.cjt.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/management/quartz")
public class QuartzController extends BaseController {

  @RequestMapping("/")
  public String index(){
    return "management/quartz/index";
  }
}
