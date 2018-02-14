package com.cjt.admin.job;

import com.alibaba.fastjson.JSONObject;
import com.cjt.common.util.ExceptionUtils;
import com.cjt.common.util.StreamUtils;
import com.cjt.dao.IVideoDAO;
import com.cjt.entity.model.Video;
import com.cjt.service.http.service.IJapaneseService;
import com.cjt.service.http.service.IQiniuService;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author caojiantao
 * @since 2018-02-14 15:16:20
 */
@Component
public class JapaneseVideoJob implements Job {

    private static final Logger logger = Logger.getLogger(JapaneseVideoJob.class);

    @Autowired
    private IJapaneseService japaneseService;

    @Autowired
    private IVideoDAO videoDAO;

    @Autowired
    private IQiniuService qiniuService;

    @Value("${file_api_url}")
    private String fileApiUrl;

    @Value("${ku_src}")
    private String srcUrl;

    @Value("${qiniu_url}")
    private String url;

    @Override
    @SuppressWarnings("unchecked")
    public void execute(JobExecutionContext context) {
        logger.info("【" + context.getJobDetail().getDescription() + "】开始执行...");
        List<String> vids = videoDAO.listVid();
        int page = 1;
        // 同步最近一页的数据
        while (page <= 1) {
            JSONObject result = japaneseService.listVideo(page++, 25);
            List<Video> videos = result.getObject("data", List.class);
            for (Video video : videos) {
                // 判断是否已经下载存储过
                if (vids.contains(video.getVid())) {
                    continue;
                }
                String indexUrl = japaneseService.getVideoSrc(video.getVid());
                try (InputStream is = StreamUtils.getInputStream(indexUrl);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                    String bucket = "static";
                    String key = "m3u8/" + video.getVid() + ".m3u8";
                    // 按行读取，更改视频切片地址，避免跨域问题
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.endsWith(".ts")) {
                            line = fileApiUrl + "?src=" + URLEncoder.encode(srcUrl + line, "UTF-8");
                        }
                        builder.append(line);
                        builder.append(System.getProperty("line.separator", "\n"));
                    }
                    indexUrl = qiniuService.uploadFile(builder.toString().getBytes(Charset.forName("utf-8")), bucket, key);
                    video.setIndexUrl(indexUrl);
                    videoDAO.saveVideo(video);
                } catch (IOException e) {
                    logger.error(ExceptionUtils.toDetailStr(e));
                }
            }
        }
        logger.info("【" + context.getJobDetail().getDescription() + "】执行完毕...");
    }
}
