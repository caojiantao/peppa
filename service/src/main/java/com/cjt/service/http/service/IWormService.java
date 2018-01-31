package com.cjt.service.http.service;

import com.alibaba.fastjson.JSONArray;

import java.io.InputStream;

/**
 * @author caojiantao
 * @since 2018-01-31 01:20:51
 */
public interface IWormService {

    /**
     * 获取可用的动态IP，端口
     *
     * @return 可用IP，端口
     */
    JSONArray listIpPort();

    /**
     * 动态代理IP和端口获取指定地址的输入流
     *
     * @param url  请求地址
     * @param ip   代理IP
     * @param port 代理端口
     * @return inputstream流
     */
    InputStream getInputStreamByDynamicIpPort(String url, String ip, int port);
}
