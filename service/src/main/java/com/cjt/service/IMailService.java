package com.cjt.service;

import java.util.Map;

/**
 * @author caojiantao
 */
public interface IMailService {

    /**
     * 发送一个VM模板型邮件
     *
     * @param subject  主题
     * @param toAddrs  收件人地址（支持群发）
     * @param modelMap vm渲染参数map
     * @return 发送邮件成功状态
     */
    boolean sendEmailByVelocityEngine(String subject, String[] toAddrs, Map<String, Object> modelMap);
}
