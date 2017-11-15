package com.cjt.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.cjt.api.HelloHessian;
import com.cjt.common.dto.BasePageDto;
import com.cjt.common.entity.User;
import com.cjt.common.util.FileUtil;
import com.cjt.entity.admin.Quartz;
import com.cjt.service.IQuartzService;
import com.cjt.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author caojiantao
 * 后台演示的各种demo
 */
@Controller
@RequestMapping("/demo")
public class DemoController extends BaseController {

  @Resource
  private UserService userService;

  @Resource
  private IQuartzService quartzService;

  @Resource
  private HelloHessian helloHessian;

  @RequestMapping("/quartz")
  public String quartz() {
    return "demo/quartz";
  }

  @ResponseBody
  @RequestMapping(value = "/quartz/jobs", method = RequestMethod.GET)
  public JSONObject listQuartzJobs(BasePageDto dto) {
    JSONObject object = new JSONObject();
    List<Quartz> jobs = quartzService.listJobs(dto);
    int count = quartzService.countJobs(dto);
    object.put("result", jobs);
    object.put("count", count);
    return object;
  }

  @RequestMapping("login")
  public String login() {
    return "login";
  }

  @RequestMapping("/test")
  @ResponseBody
  public void test() {
    List<User> users = userService.listAllUsers();
    for (int i = 0; i < 2; i++) {
      users.get(i).setAge(i);
    }
    userService.updateUsers(users.subList(0, 2));
    String str = null;
    str.length();
  }

  @RequestMapping(value = "a/{id}", method = RequestMethod.GET)
  @ResponseBody
  public String a(@PathVariable String id) throws IOException {
    return id;
  }

  @RequestMapping("/cache")
  @ResponseBody
  public  Quartz cache(String name) {
    Quartz t = quartzService.getQuartz(name);
    return t;
  }

  @Value("${upload_path}")
  private String uploadPath;

  @RequestMapping("uploadFile")
  @ResponseBody
  public boolean uploadFile(MultipartFile file) throws IOException {
    File tarFile = new File(uploadPath, file.getOriginalFilename());
    return FileUtil.copyFileByChannel((FileInputStream) file.getInputStream(), tarFile);
  }

  @RequestMapping("/error/ajax")
  public String ajaxError() {
    return "demo/ajaxError";
  }
}