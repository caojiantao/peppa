package com.cjt.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author caojiantao
 */
@Controller
@RequestMapping("/management/quartz")
public class QuartzController extends BaseController {

    @RequestMapping("/")
    public String index() {
        return "management/quartz/index";
    }
}
