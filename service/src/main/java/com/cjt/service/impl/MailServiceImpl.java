package com.cjt.service.impl;

import com.cjt.service.IMailService;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Map;

/**
 * @author caojiantao
 */
@Service
public class MailServiceImpl implements IMailService {

    private final JavaMailSender mailSender;

    private final FreeMarkerConfig freeMarkerConfig;

    @Value("${mail.username}")
    private String from;

    @Value("${mail.filename}")
    private String templateName;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender, FreeMarkerConfig freeMarkerConfig) {
        this.mailSender = mailSender;
        this.freeMarkerConfig = freeMarkerConfig;
    }

    @Override
    public boolean sendEmailByTemplate(String subject, String[] toAddrs, Map<String, Object> modelMap) {
        try {
            Template template = freeMarkerConfig.getConfiguration().getTemplate(templateName);
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, modelMap);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(toAddrs);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
        return false;
    }
}
