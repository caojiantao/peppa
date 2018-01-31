package com.cjt.service.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjt.service.http.service.ISongService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
        String params = "keyword=" + keyword + "&page=" + page + "&pagesize=" + pagesize;
        params += "&platform=WebFilter";
        HttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(SONG_SEARCH + "?" + params);
        JSONArray songs = null;
        int total = 0;
        try {
            HttpResponse response = client.execute(httpGet);
            // 将content的输入流直接转换为JSONObject
            JSONObject object = JSONObject.parseObject(response.getEntity().getContent(), JSONObject.class);
            // 获取歌曲list
            songs = object.getJSONObject("data").getJSONArray("lists");
            total = object.getJSONObject("data").getInteger("total");
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject result = new JSONObject();
        result.put("data", songs);
        result.put("total", total);
        return result;
    }

    @Override
    public JSONObject getSongPlay(String fileHash, String albumId) {
        JSONObject songPlay = null;
        String data = "r=play/getdata&hash=" + fileHash + "&album_id=" + albumId;
        HttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(SONG + "?" + data);
        try {
            HttpResponse response = client.execute(httpGet);
            songPlay = JSONObject.parseObject(response.getEntity().getContent(), JSONObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songPlay;
    }
}
