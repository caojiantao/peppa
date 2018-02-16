package com.cjt.dao;

import com.cjt.entity.dto.VideoDTO;
import com.cjt.entity.model.Video;

import java.util.List;

/**
 * @author caojiantao
 * @since 2018-02-12 00:29:43
 */
public interface IVideoDAO {

    List<Video> listVideo(VideoDTO dto);

    int countVideo(VideoDTO dto);

    int saveVideo(Video video);

    List<String> listVid();

    Video getVideoById(int id);
}
