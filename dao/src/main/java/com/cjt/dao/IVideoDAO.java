package com.cjt.dao;

import com.cjt.entity.model.Video;

import java.util.List;

/**
 * @author caojiantao
 * @since 2018-02-12 00:29:43
 */
public interface IVideoDAO {

    List<Video> listVideo(int start, int offset);

    int countVideo();

    int saveVideo(Video video);

    List<String> listVid();

    int saveVideos(List<Video> videos);

    int removeVideoByVid(int vid);
}
