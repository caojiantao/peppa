package com.cjt.service.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjt.dao.http.ISrcIpPortDao;
import com.cjt.entity.model.http.SrcIpPort;
import com.cjt.service.http.service.IJapaneseService;
import com.cjt.service.http.service.IWormService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author caojiantao
 */
@Service
public class JapaneseServiceImpl implements IJapaneseService {

    private static final String CENTER_URL = "https://ku188.top";

    private static final String API_URL = "https://www.ku188.top";

    private static final String SRC_URL = "https://cf.hls2.top";

    @Autowired
    private IWormService wormService;

    @Autowired
    private ISrcIpPortDao srcIpPortDao;

    @Override
    public JSONObject listVideo(int page, int pagesize) {
        JSONObject result = new JSONObject();

        try {
            Document document = Jsoup.connect(CENTER_URL + "/video/")
                    // 模拟客户端，防止被拦截
                    .userAgent("Chrome")
                    .data("PageNo", String.valueOf(page))
                    .get();
            Element container = document.getElementsByClass("blog-posts").first();
            Elements posts = container.getElementsByClass("blog-post");
            JSONArray data = new JSONArray();
            for (Element element : posts) {
                JSONObject post = new JSONObject();
                // 得到VID还包含video字符串，剪掉
                post.put("vid", element.getElementsByTag("a").first().attr("href").replace("/video/", ""));
                post.put("title", element.getElementsByClass("blog-title").first().attr("title"));
                post.put("img", element.getElementsByTag("img").first().attr("src"));
                post.put("date", element.getElementsByClass("sub_head").first().text());
                data.add(post);
            }
            result.put("data", data);

            Element pagination = document.getElementsByClass("pageinfo").first();
            int total = Integer.parseInt(pagination.getElementsByTag("strong").last().text());
            result.put("total", total);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String getFileSrc(String vid) {
        Map<String, SrcIpPort> srcIpPortMap = getSrcIpPortMap();
        try {
            String requestUrl = API_URL + "/video/" + vid;
            Document document = Jsoup.connect(requestUrl)
                    // 模拟客户端，防止被拦截
                    .userAgent("Chrome")
                    .ignoreContentType(true)
                    // 添加referrer，避免读取不到数据
                    .referrer(API_URL + "/video/" + vid)
                    .get();
            JSONObject result = JSONObject.parseObject(document.text());
            if (result != null) {
                if (!StringUtils.isEmpty(result.getString("data"))) {
                    return SRC_URL + result.getString("data");
                }
            }
            // 否则设置动态IP
            return parseDynamic(requestUrl, 0, wormService.listIpPort(), srcIpPortMap);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Map<String, SrcIpPort> getSrcIpPortMap() {
        List<SrcIpPort> srcIpPorts = srcIpPortDao.listSrcIpPort();
        Map<String, SrcIpPort> srcIpPortMap = new HashMap<>();
        if (srcIpPorts != null) {
            for (SrcIpPort srcIpPort : srcIpPorts) {
                srcIpPortMap.put(srcIpPort.getSrcUrl(), srcIpPort);
            }
        }
        return srcIpPortMap;
    }

    private String parseDynamic(String url, int count, JSONArray dynamicIpPort, Map<String, SrcIpPort> srcIpPortMap) {
        if (count > 10) {
            return null;
        }
        String ip;
        int port;
        // 根据请求URL匹配数据库 IP PORT
        SrcIpPort srcIpPort = srcIpPortMap.get(url);
        if (srcIpPort == null) {
            // 随机取可用动态IP
            JSONObject ipPort = dynamicIpPort.getJSONObject((new Random()).nextInt(dynamicIpPort.size()));
            ip = ipPort.getString("ip");
            port = ipPort.getInteger("port");
        } else {
            ip = srcIpPort.getIp();
            port = srcIpPort.getPort();
        }
        InputStream is = wormService.getInputStreamByDynamicIpPort(url, ip, port);
        if (is == null) {
            return parseDynamic(url, ++count, dynamicIpPort, srcIpPortMap);
        }
        JSONObject result;
        try {
            result = JSONObject.parseObject(is, JSONObject.class);
            if (result == null || StringUtils.isEmpty(result.getString("data"))) {
                return parseDynamic(url, ++count, dynamicIpPort, srcIpPortMap);
            }
            if (srcIpPort == null) {
                // 动态IP获取数据成功，存储VID IP PORT对应关系至数据库
                srcIpPort = new SrcIpPort(url, ip, port);
                srcIpPortDao.removeSrcIpPort(url);
                srcIpPortDao.saveSrcIpPort(srcIpPort);
            }
            return SRC_URL + result.getString("data");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
//
//    public static void main(String[] args) {
//        IJapaneseService japaneseService = new JapaneseServiceImpl();
//        System.out.println(japaneseService.getFileSrc("4479"));
//    }
}
