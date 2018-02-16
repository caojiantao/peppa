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

    @GetMapping("/buckets")
    public Object listBucket() {
        return qiniuService.listBucket();
    }

    @PostMapping("/buckets/{bucket}/files")
    public Object upload(@PathVariable("bucket") String bucket, MultipartFile file, String key) {
        try {
            return qiniuService.uploadFile(StreamUtils.copyToByteArray(file.getInputStream()), bucket, key);
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "上传图片失败";
        }
    }

    @GetMapping("/buckets/{bucket}/files")
    public Object listFiles(@PathVariable("bucket") String bucket, String prefix) {
        return qiniuService.listFiles(bucket, prefix);
    }

    @DeleteMapping("/buckets/{bucket}/files")
    public Object removeFile(@PathVariable("bucket") String bucket, String key) {
        if (qiniuService.removeFile(bucket, key)){
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return null;
        }
        return "删除资源失败";
    }
}
