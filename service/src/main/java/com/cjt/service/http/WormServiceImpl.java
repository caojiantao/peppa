package com.cjt.service.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjt.service.http.service.IWormService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;

/**
 * @author caojiantao
 * @since 2018-01-31 01:20:35
 */
@Service
public class WormServiceImpl implements IWormService {

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
                    .userAgent("Mozilla")
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
                object.put("ip", colElements.get(1).text());
                object.put("port", colElements.get(2).text());
                data.add(object);
            }
        }
        return data;
    }

    @Override
    public InputStream getInputStreamByDynamicIpPort(String url, String ip, int port) {
        SocketAddress address = new InetSocketAddress(ip, port);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection(proxy);
            connection.setConnectTimeout(3000);
            connection.setRequestProperty("Referer", url);
            connection.setRequestProperty("User-Agent", "Chrome");
            connection.connect();
            return connection.getInputStream();
        } catch (IOException e) {
            return null;
        }
    }
}
