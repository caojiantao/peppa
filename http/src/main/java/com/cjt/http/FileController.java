package com.cjt.http;

import com.cjt.common.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/files")
public class FileController {

    @GetMapping("")
    public void getTsInputStream(String src, HttpServletResponse response) {
        // 强制下载
        response.setContentType("application/force-download");
        try (InputStream is = StreamUtils.getInputStream(URLDecoder.decode(src, "UTF-8"));
             BufferedInputStream bis = new BufferedInputStream(is);
             OutputStream os = response.getOutputStream()) {
            byte[] buffers = new byte[1024 * 1024];
            int len;
            while ((len = bis.read(buffers)) != -1) {
                os.write(buffers, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
