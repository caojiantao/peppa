package com.cjt.common.util;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author caojiantao
 */
public class HttpUtils {

    private final static String UTF_8 = "utf-8";

    private final static String GET = "GET";

    private final static String POST = "POST";

    private final static String COOKIE = "Cookie";

    private final static String USER_AGENT = "User-Agent";

    private final static String REFERER = "Referer";

    private final static String LINE_SEPARATOR = "line.separator";

    private String url;

    private Map<String, String> headers;

    private Map<String, String> cookies;

    private Map<String, String> data;

    private int timeout;

    private String method;

    private Proxy proxy;

    private Callback callback;

    public static HttpUtils connect(String url) {
        HttpUtils utils = new HttpUtils();
        utils.url = url;
        utils.headers = new HashMap<>(1 << 3);
        utils.cookies = new HashMap<>(1 << 3);
        utils.data = new HashMap<>(1 << 3);
        utils.timeout = 6000;
        utils.method = "GET";
        utils.proxy = null;
        utils.callback = null;
        return utils;
    }

    public HttpUtils header(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public HttpUtils cookie(String key, String value) {
        cookies.put(key, value);
        return this;
    }

    public HttpUtils data(String key, String value) {
        data.put(key, value);
        return this;
    }

    public HttpUtils userAgent(String value) {
        headers.put(USER_AGENT, value);
        return this;
    }

    public HttpUtils referer(String value) {
        headers.put(REFERER, value);
        return this;
    }

    public HttpUtils proxy(String ip, int port) {
        SocketAddress address = new InetSocketAddress(ip, port);
        proxy = new Proxy(Proxy.Type.HTTP, address);
        return this;
    }

    public HttpUtils callback(Callback callback) {
        this.callback = callback;
        return this;
    }

    public InputStream execute() throws IOException {
        if (callback != null) {
            new Thread(() -> {
                try {
                    InputStream is = doHttp();
                    callback.handle(is);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            return null;
        } else {
            return doHttp();
        }
    }

    private InputStream doHttp() throws IOException {
        url += GET.equalsIgnoreCase(method) ? getParamStr(data) : "";
        HttpURLConnection connection = (proxy == null) ? (HttpURLConnection) new URL(url).openConnection() : (HttpURLConnection) new URL(url).openConnection(proxy);
        connection.setConnectTimeout(timeout);
        connection.setRequestMethod(method);
        headers.forEach(connection::addRequestProperty);
        connection.addRequestProperty(COOKIE, getCookieStr(cookies));
        if (POST.equalsIgnoreCase(method)) {
            connection.setDoOutput(true);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), UTF_8));
            writer.write(getParamStr(data));
            writer.flush();
            writer.close();
        }
        return connection.getInputStream();
    }

    public InputStream get() throws IOException {
        this.method = GET;
        return execute();
    }

    public InputStream post() throws IOException {
        this.method = POST;
        return execute();
    }

    private String getParamStr(Map<String, String> data) throws UnsupportedEncodingException {
        if (!data.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            Set<Map.Entry<String, String>> entries = data.entrySet();
            builder.append("?");
            for (Map.Entry<String, String> entry : entries) {
                // value encode，避免特殊字符
                builder.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), UTF_8)).append("&");
            }
            return builder.substring(0, builder.length() - 1);
        }
        return "";
    }

    private String getCookieStr(Map<String, String> cookies) {
        StringBuilder builder = new StringBuilder();
        cookies.forEach((key, value) -> builder.append(key).append("=").append(value).append(";"));
        return builder.toString();
    }

    public static String getStringFromInputStream(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, UTF_8));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append(System.getProperty(LINE_SEPARATOR, "\n"));
        }
        return builder.toString();
    }

    public interface Callback {

        /**
         * 请求回调处理
         *
         * @param is 输入流
         * @throws IOException 请求异常
         */
        void handle(InputStream is) throws IOException;
    }
}
