package com.cjt.http.kugou;

import com.alibaba.fastjson.JSONObject;
import com.cjt.service.http.service.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caojiantao
 * @since 2018-01-12 21:26:40
 */
@RestController
@RequestMapping("/kugou")
public class KugouController {

    private final ISongService songService;

    @Autowired
    public KugouController(ISongService songService) {
        this.songService = songService;
    }

    @GetMapping("/songs")
    public JSONObject listSongPage(String keyword, @RequestParam(value = "1", required = false) int page, @RequestParam(value = "20", required = false) int pagesize) {
        return songService.listSong(keyword, page, pagesize);
    }

    @GetMapping("/songs/play")
    public JSONObject getSongPlay(String fileHash, String albumId) {
        return songService.getSongPlay(fileHash, albumId);
    }

    @GetMapping("/mvs")
    public JSONObject listMv(String keyword, @RequestParam(value = "1", required = false) int page, @RequestParam(value = "20", required = false) int pagesize) {
        return songService.listMv(keyword, page, pagesize);
    }

    @GetMapping("/mvs/play")
    public JSONObject getMvPlay(String hash) {
        return songService.getMvPlay(hash);
    }
}
