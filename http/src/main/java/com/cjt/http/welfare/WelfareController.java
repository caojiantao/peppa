package com.cjt.http.welfare;

import com.cjt.http.BaseController;
import com.cjt.service.http.service.IJapaneseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @author caojiantao
 * @since 2018-01-29 01:37:46
 */
@RestController
@RequestMapping("/welfare")
public class WelfareController extends BaseController {

    @Autowired
    private IJapaneseService japaneseService;

    @Value("${file_api_url}")
    private String fileApiUrl;

    @GetMapping("/videos")
    public Object listVideos(int page, int pagesize) {
        return japaneseService.listVideo(page, pagesize);
    }

    @GetMapping("/videos/{id}")
    public Object getVideoById(@PathVariable("id") String id) throws UnsupportedEncodingException {
        String fileSrc = URLEncoder.encode(japaneseService.getVideoSrc(id), "UTF-8");
        return fileApiUrl + "?src=" + fileSrc;
    }
}
