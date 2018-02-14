package com.cjt.common.util;

import java.io.*;
import java.net.*;

/**
 * @author caojiantao
 */
public class StreamUtils extends org.springframework.util.StreamUtils {

    /**
     * 输入流转换为字符串，按行读取
     *
     * @param is 输入流
     * @return 结果字符串
     */
    public String readFromStream(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append(System.getProperty("line.separator", "\n"));
        }
        return builder.toString();
    }

    public static InputStream getInputStream(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("User-Agent", "chrome");
        connection.connect();
        return connection.getInputStream();
    }

    public static InputStream getInputStream(String url, String ip, int port) throws IOException {
        SocketAddress address = new InetSocketAddress(ip, port);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection(proxy);
        connection.setRequestProperty("Referer", url);
        connection.setRequestProperty("User-Agent", "chrome");
        connection.connect();
        return connection.getInputStream();
    }
}
