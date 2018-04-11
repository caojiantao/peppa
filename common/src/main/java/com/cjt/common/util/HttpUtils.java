package com.cjt.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * @author caojiantao
 */
public class HttpUtils {

    public static InputStream getInputStream(String url, Map<String, String> requestProperty, long timeout, Proxy proxy) throws IOException {
        InputStream is = null;
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection(proxy);
        connection.addRequestProperty("Referer", url);
        connection.connect();
        return is;
    }

    private static String toCookies(Map<String, String> map) {
        Set<Map.Entry<String, String>> entries = map.entrySet();
        StringBuilder builder = new StringBuilder();
        entries.forEach(entry -> {
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(entry.getValue());
            builder.append(";");
        });
        return builder.toString();
    }
}
