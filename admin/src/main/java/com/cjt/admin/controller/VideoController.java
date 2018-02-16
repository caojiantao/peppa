package com.cjt.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.cjt.entity.dto.VideoDTO;
import com.cjt.entity.model.Video;
import com.cjt.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author caojiantao
 * @since 2018-02-16 13:46:09
 */
@RestController
@RequestMapping("/videos")
public class VideoController extends BaseController {

    @Autowired
    private IVideoService videoService;

    @GetMapping("")
    public JSONObject listVideo(VideoDTO dto) {
        return videoService.listVideo(dto);
    }

    @GetMapping("/{id}")
    public Object getVideoById(@PathVariable("id") int id) {
        Video video = videoService.getVideoById(id);
        if (video == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return video;
    }
}
