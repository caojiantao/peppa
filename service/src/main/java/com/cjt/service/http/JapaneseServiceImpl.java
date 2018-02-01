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
        String url = API_URL + "/video/" + vid;

        boolean isVisitLocal = false;

        // 获取数据库中已保存的“资源-IP-端口”对应关系，和本机localhost是否已经有关联资源
        Map<String, SrcIpPort> srcIpPortMap = new HashMap<>();
        List<SrcIpPort> srcIpPorts = srcIpPortDao.listSrcIpPort();
        if (srcIpPorts != null) {
            for (SrcIpPort srcIpPort : srcIpPorts) {
                if ("localhost".equals(srcIpPort.getIp())) {
                    isVisitLocal = true;
                }
                srcIpPortMap.put(srcIpPort.getSrcUrl(), srcIpPort);
            }
        }

        SrcIpPort srcIpPort = srcIpPortMap.get(url);
        // 根据请求资源地址匹配数据库查询
        if (srcIpPort != null) {
            if ("localhost".equals(srcIpPort.getIp())) {
                // 本地已经访问过
                return parseLocal(url, false);
            } else {
                // 动态IP代理
                return parseDynamic(url, srcIpPort.getIp(), srcIpPort.getPort(), wormService.listIpPort());
            }
        } else {
            if (isVisitLocal) {
                // 随机动态IP代理
                return parseDynamic(url, wormService.listIpPort());
            } else {
                // 本机可首次访问
                return parseLocal(url, true);
            }
        }
    }

    private String parseLocal(String url, boolean isSave) {
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
                    if (isSave) {
                        srcIpPortDao.removeSrcIpPort(url);
                        srcIpPortDao.saveSrcIpPort(new SrcIpPort(url, "localhost", 80));
                    }
                    return SRC_URL + data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 本地再次失败采用动态代理
        srcIpPortDao.removeSrcIpPort(url);
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
        InputStream is = wormService.getInputStreamByDynamicIpPort(url, ip, port);
        if (is == null) {
            srcIpPortDao.removeSrcIpPort(url);
            return parseDynamic(url, dynamicIpPort);
        }
        JSONObject result;
        try {
            result = JSONObject.parseObject(is, JSONObject.class);
            if (result == null || StringUtils.isEmpty(result.getString("data"))) {
                srcIpPortDao.removeSrcIpPort(url);
                return parseDynamic(url, dynamicIpPort);
            }
            SrcIpPort srcIpPort = new SrcIpPort(url, ip, port);
            // 存储需要注意
            srcIpPortDao.removeSrcIpPort(url);
            srcIpPortDao.saveSrcIpPort(srcIpPort);
            return SRC_URL + result.getString("data");
        } catch (IOException e) {
            e.printStackTrace();
            return parseDynamic(url, dynamicIpPort);
        }
    }
}
