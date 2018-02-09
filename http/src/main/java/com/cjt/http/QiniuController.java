package com.cjt.http;

import com.cjt.service.http.service.IQiniuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/qiniu")
public class QiniuController extends BaseController {

    @Autowired
    private IQiniuService qiniuService;

    @PostMapping("/{bucket}")
    public Object upload(@PathVariable("bucket") String bucket, MultipartFile file, String key) {
        try {
            return qiniuService.uploadFile(StreamUtils.copyToByteArray(file.getInputStream()), bucket, key);
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "上传图片失败";
        }
    }

    @GetMapping("/{bucket}")
    public Object listFiles(@PathVariable("bucket") String bucket, int page, int pagesize) {
        return qiniuService.listFiles(bucket, page, pagesize);
    }

    @DeleteMapping("/{bucket}/{key}")
    public Object removeFile(@PathVariable("bucket") String bucket, @PathVariable("key") String key) {
        return qiniuService.removeFile(bucket, key);
    }
}
