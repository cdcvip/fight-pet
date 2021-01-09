package com.chone.fightpet.common;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chone
 * HttpUtils
 */
@Slf4j
public class HttpUtils {
    private final static OkHttpClient HTTP_CLIENT = new OkHttpClient();
    /**
     * 正则表达式:截取域名
     */
    private final static Pattern HTTP_PATTERN = Pattern.compile("[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\\.?");

    public static String doPostForm(String url, boolean isJson, String content, Map<String, String> header) {
        MediaType mediaType;
        if (isJson) {
            mediaType = MediaType.parse("text/plain");
        } else {
            mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
        }
        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .headers(Headers.of(header))
                .build();
        return getResponseBody(request, url);
    }

    public static String doGet(String url, Map<String, String> header) {
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .headers(Headers.of(header))
                .build();
        return getResponseBody(request, url);
    }

    public static String doGet(String url, String cookie) {
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("cookie",cookie)
                .build();
        return getResponseBody(request, url);
    }

    private static String getResponseBody(Request request, String url) {
        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                return responseBody.string();
            }
        } catch (Exception e) {
            Matcher matcher = HTTP_PATTERN.matcher(url);
            if (matcher.find()) {
                url = matcher.group();
            }
            log.warn("Request failed [{}]", url);
        }
        return null;
    }
}