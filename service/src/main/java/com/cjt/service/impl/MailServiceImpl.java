package com.cjt.service.impl;

import com.cjt.service.IMailService;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service
public class MailServiceImpl implements IMailService {

  @Resource
  private JavaMailSender mailSender;

  @Resource
  private VelocityEngine velocityEngine;

  @Value("${mail.username}")
  private String from;

  @Override
  public boolean sendEmailByVelocityEngine(String subject, String[] toAddrs, Map<String, Object> modelMap) {
    try {
      String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "mail.vm", "UTF-8", modelMap);
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
      helper.setFrom(from);
      helper.setTo(toAddrs);
      helper.setSubject(subject);
      helper.setText(content, true);
      mailSender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    return false;
  }
}
