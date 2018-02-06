package com.cjt.common.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author caojiantao
 */
public class StreamUtils {

    /**
     * 输入流转换为字符串，按行读取
     *
     * @param is 输入流
     * @return 结果字符串
     */
    public String readFromStream(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder builder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator", "\n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static InputStream getInputStream(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("User-Agent", "chrome");
        connection.connect();
        return connection.getInputStream();
    }
}
