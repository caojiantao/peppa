package com.cjt.http.kugou;

import com.alibaba.fastjson.JSONObject;
import com.cjt.service.http.service.ISongService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author caojiantao
 * @since 2018-01-12 21:26:40
 */
@RestController
@RequestMapping("/kugou")
public class KugouController {

    @Resource
    private ISongService songService;

    @RequestMapping("/songs")
    public JSONObject listSongPage(String keyword, int page, int pagesize) throws UnsupportedEncodingException {
        return songService.listSong(URLEncoder.encode(keyword, "utf-8"), page, pagesize);
    }

    @RequestMapping("/songs/play")
    public JSONObject getSongPlay(String fileHash, String albumId) {
        return songService.getSongPlay(fileHash, albumId);
    }
}
