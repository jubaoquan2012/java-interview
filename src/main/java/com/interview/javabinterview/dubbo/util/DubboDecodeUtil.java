package com.interview.javabinterview.dubbo.util;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/16
 */
public class DubboDecodeUtil {



    public static String asciiToString(String url) {
        url = url.replaceAll("%3A", ":").replaceAll("%2F", "/")  //过滤URL 包含中文
                .replaceAll("%3F", "?").replaceAll("%3D", "=").replaceAll(
                        "%26", "&");
        return url;
    }
}
