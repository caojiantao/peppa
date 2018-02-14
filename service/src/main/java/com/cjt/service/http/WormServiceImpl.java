package com.cjt.service.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjt.service.http.service.IWormService;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author caojiantao
 * @since 2018-01-31 01:20:35
 */
@Service
public class WormServiceImpl implements IWormService {

    private final static Logger logger = Logger.getLogger(WormServiceImpl.class);

    /**
     * 动态IP端口解析地址，后面加"/1"代表第一页
     */
    private static String DYNAMIC_IP_URL = "http://www.xicidaili.com/nt";

    @Override
    public JSONArray listIpPort() {
        JSONArray data = new JSONArray();
        Document document;
        try {
            document = Jsoup.connect(DYNAMIC_IP_URL)
                    .userAgent("chrome")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Element ipListElement = document.getElementById("ip_list");
        Elements rowElements = ipListElement.getElementsByTag("tr");
        for (Element element : rowElements) {
            Elements colElements = element.getElementsByTag("td");
            if (!colElements.isEmpty()) {
                JSONObject object = new JSONObject();
                String timeoutStr = colElements.get(6).getElementsByClass("bar").first().attr("title");
                double timeout = Double.parseDouble(timeoutStr.replace("秒", ""));
                if (timeout > 1) {
                    // 过滤掉速度超过1s的
                    continue;
                }
                object.put("timeout", timeout);
                object.put("ip", colElements.get(1).text());
                object.put("port", colElements.get(2).text());
                data.add(object);
            }
        }
        return data;
    }
}
