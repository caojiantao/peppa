package com.cjt.service.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjt.common.util.ExceptionUtils;
import com.cjt.common.util.StreamUtils;
import com.cjt.entity.model.Video;
import com.cjt.service.http.service.IJapaneseService;
import com.cjt.service.http.service.IWormService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author caojiantao
 */
@Service
public class JapaneseServiceImpl implements IJapaneseService {

    private static final Logger logger = Logger.getLogger(JapaneseServiceImpl.class);

    @Autowired
    private IWormService wormService;

    @Value("${ku_center}")
    private String centerUrl;

    @Value("${ku_api}")
    private String apiUrl;

    @Value("${ku_src}")
    private String srcUrl;

    @Value("${file_api_url}")
    private String fileApiUrl;

    @Override
    public JSONObject listVideo(int page, int pagesize) {
        JSONObject result = new JSONObject();
        try {
            Document document = Jsoup.connect(centerUrl + "/video/")
                    // 模拟客户端，防止被拦截
                    .userAgent("Chrome")
                    .data("PageNo", String.valueOf(page))
                    .get();
            Element container = document.getElementsByClass("blog-posts").first();
            Elements posts = container.getElementsByClass("blog-post");
            List<Video> videos = new ArrayList<>();
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            for (Element element : posts) {
                Video video = new Video();
                // 得到vid还包含"/video/"字符串，减掉
                String href = element.getElementsByTag("a").first().attr("href");
                video.setVid(href.substring(7));
                video.setTitle(element.getElementsByClass("blog-title").first().attr("title"));
                video.setPosterUrl(element.getElementsByTag("img").first().attr("src"));
                // 得到上架时间，还包含"上架時間:"字样，需要格式化
                String dateStr = element.getElementsByClass("sub_head").first().text();
                video.setDate(format.parse(dateStr.substring(5)));
                videos.add(video);
            }
            result.put("data", videos);

            Element pagination = document.getElementsByClass("pageinfo").first();
            int total = Integer.parseInt(pagination.getElementsByTag("strong").last().text());
            result.put("total", total);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String getVideoSrc(String vid) {
        String url = apiUrl + "/video/" + vid;
        return parseLocal(url);
    }

    private String parseLocal(String url) {
        try {
            Document document = Jsoup.connect(url)
                    // 模拟客户端，防止被拦截
                    .userAgent("Chrome")
                    .ignoreContentType(true)
                    // 添加referrer，避免读取不到数据
                    .referrer(url)
                    .get();
            JSONObject result = JSONObject.parseObject(document.text());
            if (result != null) {
                String data = result.getString("data");
                if (!StringUtils.isEmpty(data)) {
                    return srcUrl + data;
                }
            }
        } catch (IOException e) {
            logger.error(ExceptionUtils.toDetailStr(e));
        }
        return parseDynamic(url, wormService.listIpPort());
    }

    /**
     * 随机取动态IP端口代理请求
     *
     * @param url           请求地址
     * @param dynamicIpPort 可用的代理IP端口
     * @return 资源地址
     */
    private String parseDynamic(String url, JSONArray dynamicIpPort) {
        JSONObject ipPort = dynamicIpPort.getJSONObject((new Random()).nextInt(dynamicIpPort.size()));
        String newIp = ipPort.getString("ip");
        int newPort = ipPort.getInteger("port");
        return parseDynamic(url, newIp, newPort, dynamicIpPort);
    }

    private String parseDynamic(String url, String ip, int port, JSONArray dynamicIpPort) {
        try (InputStream is = StreamUtils.getInputStream(url, ip, port)) {
            JSONObject result = JSONObject.parseObject(is, JSONObject.class);
            if (result == null || StringUtils.isEmpty(result.getString("data"))) {
                removeInvalidData(ip, port, dynamicIpPort);
                return parseDynamic(url, dynamicIpPort);
            }
            return srcUrl + result.getString("data");
        } catch (IOException e) {
            logger.error("动态IP代理访问失败：" + url + ", " + ip + ", " + port);
            logger.error(ExceptionUtils.toDetailStr(e));
            removeInvalidData(ip, port, dynamicIpPort);
            return parseDynamic(url, dynamicIpPort);
        }
    }

    /**
     * 删除失效动态IP端口数据
     */
    private void removeInvalidData(String ip, int port, JSONArray dynamicIpPort) {
        for (int index = 0; index < dynamicIpPort.size(); index++) {
            JSONObject jsonObject = (JSONObject) dynamicIpPort.get(index);
            if (jsonObject.getString("ip").equals(ip)
                    && jsonObject.getInteger("port") == port) {
                dynamicIpPort.remove(jsonObject);
            }
        }
    }
}
