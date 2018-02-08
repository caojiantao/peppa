package com.cjt.service.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjt.common.util.JsonUtils;
import com.cjt.common.util.StreamUtils;
import com.cjt.service.http.service.ISongService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author caojiantao
 * @since 2018-01-12 21:24:41
 */
@Service
public class SongServiceImpl implements ISongService {

    /**
     * 歌曲搜索地址
     */
    private String SONG_SEARCH = "http://songsearch.kugou.com/song_search_v2";

    /**
     * 歌曲播放地址
     */
    private String SONG = "http://www.kugou.com/yy/index.php";

    @Override
    public JSONObject listSong(String keyword, int page, int pagesize) {
        JSONArray songs = null;
        int total = 0;
        try {
            String content = Jsoup.connect(SONG_SEARCH)
                    .data("keyword", keyword)
                    .data("page", String.valueOf(page))
                    .data("pagesize", String.valueOf(pagesize))
                    .data("platform", "WebFilter")
                    .get()
                    .text();
            JSONObject object = JSONObject.parseObject(content, JSONObject.class);
            // 获取歌曲list
            songs = object.getJSONObject("data").getJSONArray("lists");
            total = object.getJSONObject("data").getInteger("total");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonUtils.toPageData(songs, total);
    }

    @Override
    public JSONObject getSongPlay(String fileHash, String albumId) {
        JSONObject songPlay = null;
        try {
            String content = Jsoup.connect(SONG)
                    .data("r", "play/getdata")
                    .data("hash", fileHash)
                    .data("album_id", albumId)
                    .get()
                    .text();
            songPlay = JSONObject.parseObject(content, JSONObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songPlay;
    }
}
