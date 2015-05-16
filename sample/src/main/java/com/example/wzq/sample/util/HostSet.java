package com.example.wzq.sample.util;

import com.example.wzq.sample.BuildConfig;

/**
 * Created by wzq on 15/5/4.
 */
public enum HostSet {
    LOGIN("login.json?"),
    FIND_NEWS("news/find_user_news.json?", "user_news_json"),
    FIND_ITEMS("post/get_post.json", "post_json");

    HostSet(String...host) {
        this.host = host[0];
        this.key = host.length>1?host[1]:null;
    }

    private String host;

    private String key;

    public String getKey() {
        return key;
    }

    public String getHost() {
        return BuildConfig.HOST+host;
    }

    public int getCode() {
        return this.ordinal();
    }

}
