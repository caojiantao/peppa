package com.cjt.service.http.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author caojiantao
 */
public interface IJapaneseService {

    /**
     * @param page     页码
     * @param pagesize 分页大小
     * @return 分页获取视频信息
     */
    JSONObject listVideo(int page, int pagesize);

    /**
     * @param vid VID
     * @return 源地址信息
     */
    String getFileSrc(String vid);
}
