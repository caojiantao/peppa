package com.cjt.ssm.controller;

import com.cjt.ssm.entity.User;
import com.cjt.ssm.quartz.QuartzJobManager;
import com.cjt.ssm.quartz.TestJob;
import com.cjt.ssm.service.QuartzService;
import com.cjt.ssm.service.UserService;
import com.cjt.ssm.util.FileUtil;
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

@Controller
@RequestMapping("/demo/")
public class DemoController extends BaseController {

  @Resource
  private UserService userService;

  @Resource
  private QuartzService quartzService;

  @RequestMapping("")
  public String demo(){
    return "demo";
  }

  @RequestMapping("login")
  public String login() {
    TestJob job = new TestJob();
    job.setGroup("group");
    job.setName("cjt");
    job.setDesc("定时任务");
    job.setCronExpre("0/10 * * * * ?");
    quartzService.saveQuartz(job);
    return "login";
  }

  @RequestMapping("test")
  @ResponseBody
  public void test() {
    List<User> users = userService.listAllUsers();
    for (int i = 0; i < 2; i++) {
      users.get(i).setAge(i);
    }
    userService.updateUsers(users.subList(0, 2));
    String str = null;
  }

  @RequestMapping(value = "a/{id}", method = RequestMethod.GET)
  @ResponseBody
  public String a(@PathVariable String id) throws IOException {
    return id;
  }

  @RequestMapping("cjt")
  public void cjt(String str, MultipartFile file){
    System.out.println(request.getParameter("str"));
    System.out.println(str);
  }

  @Value("${upload_path}")
  private String uploadPath;

  @RequestMapping("uploadFile")
  @ResponseBody
  public String uploadFile(MultipartFile file) throws IOException {
    File tarFile = new File(uploadPath, file.getOriginalFilename());
    FileUtil.copyFileByChannel((FileInputStream) file.getInputStream(), tarFile);
    return "上传成功";
  }
}