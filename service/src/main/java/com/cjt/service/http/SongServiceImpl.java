package com.cjt.service.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjt.common.util.ExceptionUtils;
import com.cjt.common.util.HttpUtils;
import com.cjt.common.util.JsonUtils;
import com.cjt.service.http.service.ISongService;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author caojiantao
 * @since 2018-01-12 21:24:41
 */
@Service
public class SongServiceImpl implements ISongService {

    private Logger logger = Logger.getLogger(SongServiceImpl.class);

    @Value("${kugou_song_search}")
    private String songSearch;

    @Value("${kugou_song_play}")
    private String songPlay;

    @Value("${kugou_mv_search}")
    private String mvSearch;

    @Value("${kugou_mv_play}")
    private String mvPlay;

    @Value("${kugou_mv_salt}")
    private String mvSalt;

    @Override
    public JSONObject listSong(String keyword, int page, int pagesize) {
        return listData(keyword, page, pagesize, songSearch);
    }

    @Override
    public JSONObject getSongPlay(String fileHash, String albumId) {
        JSONObject play = null;
        try (InputStream is = HttpUtils.connect(songPlay)
                .data("r", "play/getdata")
                .data("hash", fileHash)
                .data("album_id", albumId)
                .get()) {
            play = JSONObject.parseObject(HttpUtils.getStringFromInputStream(is), JSONObject.class);
        } catch (IOException e) {
            logger.error(ExceptionUtils.toDetailStr(e));
        }
        return play;
    }

    @Override
    public JSONObject listMv(String keyword, int page, int pagesize) {
        return listData(keyword, page, pagesize, mvSearch);
    }

    @Override
    public JSONObject getMvPlay(String hash) {
        JSONObject play = null;
        String key = DigestUtils.md5DigestAsHex((hash + mvSalt).getBytes());
        try (InputStream is = HttpUtils.connect(mvPlay + "/cmd=100&hash=" + hash + "&key=" + key + "&ext=mp4")
                .get()) {
            play = JSONObject.parseObject(HttpUtils.getStringFromInputStream(is), JSONObject.class);
        } catch (IOException e) {
            logger.error(ExceptionUtils.toDetailStr(e));
        }
        return play;
    }

    private JSONObject listData(String keyword, int page, int pagesize, String url) {
        JSONArray songs = null;
        int total = 0;
        try (InputStream is = HttpUtils.connect(url)
                .data("keyword", keyword)
                .data("page", String.valueOf(page))
                .data("pagesize", String.valueOf(pagesize))
                .data("platform", "WebFilter")
                .get()) {
            JSONObject object = JSONObject.parseObject(HttpUtils.getStringFromInputStream(is), JSONObject.class);
            // 获取歌曲list
            songs = object.getJSONObject("data").getJSONArray("lists");
            total = object.getJSONObject("data").getInteger("total");
        } catch (Exception e) {
            logger.error(ExceptionUtils.toDetailStr(e));
        }
        return JsonUtils.toPageData(songs, total);
    }
}
