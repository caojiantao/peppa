package com.cjt.service.http;

import com.alibaba.fastjson.JSONObject;
import com.cjt.common.util.ExceptionUtils;
import com.cjt.common.util.JsonUtils;
import com.cjt.service.http.service.IQiniuService;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author caojiantao
 */
@Service
public class QiniuServiceImpl implements IQiniuService {

    private static final Logger logger = Logger.getLogger(QiniuServiceImpl.class);

    @Value("${qiniu_url}")
    private String url;

    @Value("${qiniu_access_key}")
    private String accessKey;

    @Value("${qiniu_secret_key}")
    private String secretKey;

    @Override
    public String uploadFile(byte[] bytes, String bucket, String key) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(bytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return url + putRet.key;
        } catch (QiniuException ex) {
            logger.error(ExceptionUtils.toDetailStr(ex));
            return null;
        }
    }

    @Override
    public JSONObject listFiles(String bucket, String prefix) {
        Configuration cfg = new Configuration(Zone.zone0());
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix, 1000, "");
        List<FileInfo> infos = new ArrayList<>();
        int total;
        // 注意无须遍历全部
        if (fileListIterator.hasNext()) {
            //处理获取的file list结果
            FileInfo[] items = fileListIterator.next();
            infos.addAll(Arrays.asList(items));
        }
        total = infos.size();
        return JsonUtils.toPageData(infos, total);
    }

    @Override
    public boolean removeFile(String bucket, String key) {
        Configuration cfg = new Configuration(Zone.zone0());
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
            return true;
        } catch (QiniuException ex) {
            logger.error(ExceptionUtils.toDetailStr(ex));
            return false;
        }
    }

    @Override
    public String[] listBucket() {
        Configuration cfg = new Configuration(Zone.zone0());
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            return bucketManager.buckets();
        } catch (QiniuException e) {
            logger.error(ExceptionUtils.toDetailStr(e));
            return null;
        }
    }
}
