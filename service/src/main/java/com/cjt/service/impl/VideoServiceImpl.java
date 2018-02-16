package com.cjt.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cjt.common.util.JsonUtils;
import com.cjt.dao.IVideoDAO;
import com.cjt.entity.dto.VideoDTO;
import com.cjt.entity.model.Video;
import com.cjt.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author caojiantao
 * @since 2018-02-16 13:51:45
 */
@Service
public class VideoServiceImpl implements IVideoService {

    @Autowired
    private IVideoDAO videoDAO;

    @Override
    public JSONObject listVideo(VideoDTO dto) {
        List<Video> videos = videoDAO.listVideo(dto);
        int total = videoDAO.countVideo(dto);
        return JsonUtils.toPageData(videos, total);
    }

    @Override
    public Video getVideoById(int id) {
        return videoDAO.getVideoById(id);
    }
}
