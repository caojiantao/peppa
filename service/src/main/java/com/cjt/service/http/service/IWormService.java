package com.cjt.service.http.service;

import com.alibaba.fastjson.JSONArray;

import java.io.InputStream;

/**
 * @author caojiantao
 * @since 2018-01-31 01:20:51
 */
public interface IWormService {

    /**
     * 获取可用的动态IP，端口（确保品质，过滤速度超过1s的）
     *
     * @return 可用IP，端口
     */
    JSONArray listIpPort();
}
