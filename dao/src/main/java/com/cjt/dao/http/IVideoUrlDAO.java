package com.cjt.dao.http;

import org.apache.ibatis.annotations.Param;

/**
 * @author caojiantao
 */
public interface IVideoUrlDAO {

    String getUrlByVid(String vid);

    void saveVideoUrl(@Param("vid") String vid, @Param("indexUrl") String indexUrl);
}
