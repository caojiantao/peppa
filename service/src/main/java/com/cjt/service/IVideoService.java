package com.cjt.service;

import com.alibaba.fastjson.JSONObject;
import com.cjt.entity.dto.VideoDTO;
import com.cjt.entity.model.Video;

/**
 * @author caojiantao
 * @since 2018-02-16 13:50:48
 */
public interface IVideoService {

    JSONObject listVideo(VideoDTO dto);

    Video getVideoById(int id);
}
