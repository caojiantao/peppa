package com.cjt.entity.model;

import lombok.Data;

import java.util.Date;

/**
 * @author caojiantao
 * @since 2018-02-12 00:27:53
 */
@Data
public class Video {

    private Integer id;
    private String vid;
    private String title;
    private String posterUrl;
    private Date date;
    private String indexUrl;
}
