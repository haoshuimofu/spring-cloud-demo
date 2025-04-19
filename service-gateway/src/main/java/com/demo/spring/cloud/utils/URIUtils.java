package com.demo.spring.cloud.utils;

import org.apache.commons.lang3.tuple.Triple;

import java.net.MalformedURLException;
import java.net.URL;

public class URIUtils {

    public static Triple<String, String, String> getURLInfo(String urlStr) throws MalformedURLException {

        URL url = new URL(urlStr);
        String path = url.getPath();
        int index = urlStr.lastIndexOf(path);
        String host = urlStr.substring(0, index);
        String server = urlStr.substring(0, index + path.length());
        return Triple.of(host, path, server);
    }


    public static void main(String[] args) throws MalformedURLException {
        String urlStr = "https://www.badiu.com/s?wd=url";
        Triple<String, String, String> url = getURLInfo(urlStr);
        System.err.println(url.getLeft());
        System.err.println(url.getMiddle());
        System.err.println(url.getRight());
    }

}
