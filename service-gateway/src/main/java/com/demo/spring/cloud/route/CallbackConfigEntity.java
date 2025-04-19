package com.demo.spring.cloud.route;

import lombok.Data;

@Data
public class CallbackConfigEntity {

    private String inPath;
    private String title;
    private Boolean outPathFixed;
    private String outUrl;
    private OutUrlInfo outUrlInfo;
    private boolean sign;
    private long timeoutMillis;
    private boolean retryOnError;
    private int retryTimes;
    private int retryIntervalMillis;


    @Data
    public static class OutUrlInfo {
        private String source;
        private String parameter;
        private String path;
    }

}
