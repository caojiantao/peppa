package com.cjt.http.welfare;

import com.cjt.service.http.service.IJapaneseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caojiantao
 * @since 2018-01-29 01:37:46
 */
@RestController
@RequestMapping("/welfare")
public class WelfareController {

    @Autowired
    private IJapaneseService japaneseService;

    @GetMapping("/videos")
    public Object listVideos(int page, int pagesize) {
        return japaneseService.listVideo(page, pagesize);
    }

    @GetMapping("/videos/{id}")
    public Object getVideoById(@PathVariable("id") String id) {
        return japaneseService.getFileSrc(id);
    }
}
