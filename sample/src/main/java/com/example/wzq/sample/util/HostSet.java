package com.example.wzq.sample.util;

/**
 * Created by wzq on 15/5/4.
 */
public enum HostSet {
    LOGIN("login.json?", 1),
    FIND_NEWS("news/find_user_news.json?", 2),
    FIND_ITEMS("post/get_post.json", 3);

    HostSet(String host, int code) {
        this.code = code;
        this.host = host;
    }

    private String host;
    private int code;

    public String getHost() {
        return Constants.HOST+host;
    }

    public int getCode() {
        return code;
    }

}
